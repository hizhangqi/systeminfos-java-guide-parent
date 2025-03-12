package com.systeminfos.shardingJdbc.config;

import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 分库分表(分表)
 * 
 * @author Administrator
 *
 */
// @Deprecated
@Configuration
@PropertySource(value = { "classpath:database.properties" })
public class DataSourceConfig {

	@Autowired
	Database0Config db0;
	@Autowired
	Database1Config db1;
	@Autowired
	Database2Config db2;
	
	
	@Bean("shardingDataSource")
	public DataSource createShardingDataSource() throws SQLException {
		// 设置从库数据源集合
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("db-master", db0.createDataSource());
		dataSourceMap.put("db-slaver1", db1.createDataSource());
		dataSourceMap.put("db-slaver2", db2.createDataSource());
		
		//分表规则: user_info表规则
		TableRuleConfiguration userInfoTableRule = getUserInfoRuleConfig();
		
		MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfig.setName("dbs");
		masterSlaveRuleConfig.setMasterDataSourceName("db-master");
		masterSlaveRuleConfig.setSlaveDataSourceNames(Arrays.asList("db-slaver1", "db-slaver2"));
		masterSlaveRuleConfig.setLoadBalanceAlgorithm(MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN.getAlgorithm());

		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfig);
		shardingRuleConfig.getTableRuleConfigs().add(userInfoTableRule);
		
		Properties p = new Properties();
		p.setProperty("sql.show", Boolean.TRUE.toString());
		
		// 设置默认数据库
		DataSource datasource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<String, Object>(), p);
		return datasource;
	}
	
	/**
	 * user_info表分片策略
	 * @return
	 */
	private TableRuleConfiguration getUserInfoRuleConfig() {
		// 配置address表规则
	    TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration();
	    tableRuleConfig.setLogicTable("user_info");
	    tableRuleConfig.setActualDataNodes("dbs.user_info_${0..1}");
	    tableRuleConfig.setKeyGeneratorColumnName("user_id");
	    // 配置分库 + 分表策略
		//tableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("lit", "ds${lit % 3}"));
		tableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "db-${user_id % 3 == 0 ? 'master' : (user_id % 3 == 1 ? 'slaver1' : 'slaver2')}"));
	    tableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "user_info_${user_id % 2}"));
		return tableRuleConfig;
	}


}
