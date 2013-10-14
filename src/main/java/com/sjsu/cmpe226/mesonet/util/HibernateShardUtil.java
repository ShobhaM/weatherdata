package com.sjsu.cmpe226.mesonet.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.ShardedConfiguration;
import org.hibernate.shards.cfg.ConfigurationToShardConfigurationAdapter;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.SequentialShardAccessStrategy;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;
public class HibernateShardUtil 
{
	
	private static SessionFactory sessionFactory;
	private static final SessionFactory sessionFactoryShardMeta  = buildSessionFactoryShardMetaData();
    private static final SessionFactory sessionFactoryShardData = buildSessionFactoryShardData();
	
    /*private static SessionFactory sessionFactory;
    public static SessionFactory getDataSessionFactory() {
        return sessionFactory;
    }*/
    
    private static SessionFactory buildSessionFactoryShardMetaData() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").addResource("com/sjsu/cmpe226/mesonet/vo/WeatherMetaDataVO.hbm.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private static SessionFactory buildSessionFactoryShardData() {
    	 try
         {
             Configuration config = new Configuration();
             config.configure("hibernate0.cfg.xml");
             config.addResource("com/sjsu/cmpe226/mesonet/vo/WeatherDataShardVO.hbm.xml");
             List shardConfigs = new ArrayList();
             shardConfigs.add(new ConfigurationToShardConfigurationAdapter(new Configuration().configure("hibernate0.cfg.xml")));
             shardConfigs.add(new ConfigurationToShardConfigurationAdapter(new Configuration().configure("hibernate1.cfg.xml")));
             ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
             ShardedConfiguration shardedConfig = new ShardedConfiguration(config,shardConfigs,shardStrategyFactory);
             sessionFactory = shardedConfig.buildShardedSessionFactory();
             return sessionFactory;
         }
         catch (Throwable ex)
         {
             ex.printStackTrace();
             sessionFactory = null;
             System.err.println("Initial SessionFactory creation failed." + ex);
             throw new ExceptionInInitializerError(ex);
         }
    }
    
    public static SessionFactory getMetaSessionFactory() {
        return sessionFactoryShardMeta;
    }
    
    public static SessionFactory getDataSessionFactory() {
        return sessionFactoryShardData;
    }

    
    static ShardStrategyFactory buildShardStrategyFactory()
    {
        ShardStrategyFactory shardStrategyFactory = new ShardStrategyFactory() {
            public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
                ShardSelectionStrategy pss = new WeatherDataShardSelectionStrategy();
                ShardResolutionStrategy prs = new WeatherDataShardResolutionStrategy(shardIds);
                ShardAccessStrategy pas = new SequentialShardAccessStrategy();
                return new ShardStrategyImpl(pss, prs, pas);
            }
        };
        return shardStrategyFactory;
    }
   /* public static void shutdownHeader() {
        getMetaSessionFactory().close();
    }*/
    
    public static void shutdownData() {
        getDataSessionFactory().close();
    }
    
}