/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.tools.zookeeper;

import org.junit.Test;

/**
 * 将properties文件生成zookeeper对应的节点信息<br>
 * mp2-member-impl工程的的配置文件节点/cn_dev/provider/xx.xx.xx<br>
 * mp2-member-impl工程的的配置文件节点/cn_dev/consumer/xx.xx.xx<br>
 * 此节点信息仅仅用于开发和测试，不会用于发布测试<br>
 * 当发布时，将会使用maven替换对应工程classpath:config/zk.properties文件
 * 
 * @author Wenqiang Sun
 * @date: 2013年10月15日
 */

@SuppressWarnings("all")
public class ZookeeperManager{

    /**
     * **************************************************************** 本地配置文件目录结构说明
     * ****************************************************************.
     */
    // 工程配置文件根目录
    private static final String LOCAL_PLATFORM_URL      = "../mp2-configuration/project";

    // 二级目录：模块类型
    /** The Constant SECOND_MP2_COMMONS. */
    private static final String SECOND_MP2_COMMONS      = "/mp2-commons";

    /** The Constant SECOND_MP2_MODULES. */
    private static final String SECOND_MP2_MODULES      = "/mp2-modules";

    /** The Constant SECOND_MP2_WEB. */
    private static final String SECOND_MP2_WEB          = "/mp2-web";

    /** The Constant SECOND_MP2_SCHEDULED. */
    private static final String SECOND_MP2_SCHEDULED    = "/mp2-schedule";

    // 三级目录：工程名称
    /** The Constant THIRD_MODULE_PLATFORM. */
    private static final String THIRD_MODULE_PLATFORM   = "/mp2-platform";

    /** The Constant THIRD_MODULE_MEMBER. */
    private static final String THIRD_MODULE_MEMBER     = "/mp2-member";

    /** The Constant THIRD_MODULE_SMS. */
    private static final String THIRD_MODULE_SMS        = "/mp2-sms";

    /** The Constant THIRD_MODULE_SHOP. */
    private static final String THIRD_MODULE_SHOP       = "/mp2-shop";

    /** The Constant THIRD_MODULE_TRADE. */
    private static final String THIRD_MODULE_TRADE      = "/mp2-trade";

    /** The Constant THIRD_MODULE_DATACENTER. */
    private static final String THIRD_MODULE_DATACENTER = "/mp2-dataCenter";

    /** The Constant THIRD_MODULE_PRODUCT. */
    private static final String THIRD_MODULE_PRODUCT    = "/mp2-product";

    /** The Constant THIRD_WEB_MEMBER. */
    private static final String THIRD_WEB_MEMBER        = "/mp2-web-marketplace/mp2-web-marketplace-member";

    /** The Constant THIRD_WEB_MAINSITE. */
    private static final String THIRD_WEB_MAINSITE      = "/mp2-web-marketplace/mp2-web-marketplace-mainsite";

    /** The Constant THIRD_WEB_PRODUCT. */
    private static final String THIRD_WEB_PRODUCT       = "/mp2-web-marketplace/mp2-web-marketplace-product";

    /** The Constant THIRD_WEB_TRADE. */
    private static final String THIRD_WEB_TRADE         = "/mp2-web-marketplace/mp2-web-marketplace-trade";

    /** The Constant THIRD_WEB_SHOP. */
    private static final String THIRD_WEB_SHOP          = "/mp2-web-merchanttools";

    /** The Constant THIRD_WEB_MANAGEMENT. */
    private static final String THIRD_WEB_MANAGEMENT    = "/mp2-web-managementsystem";

    /** The Constant THIRD_LIVECHAT. */
    private static final String THIRD_LIVECHAT          = "/mp2-livechat";

    // 四级目录： 环境定义
    /** The Constant FOURTH_DEV. */
    private static final String FOURTH_DEV              = "/dev";

    /** The Constant FOURTH_PRODUCTION. */
    private static final String FOURTH_PRODUCTION       = "/production";

    /** The Constant FOURTH_STAGING. */
    private static final String FOURTH_STAGING          = "/staging";

    /** The Constant FOURTH_TEST. */
    private static final String FOURTH_TEST             = "/test";

    /**
     * **************************************************************** zookeeper节点目录结构 mp2_dev/test <br>
     * --consumer <br>
     * ----member <br>
     * --provider <br>
     * ----platform <br>
     * ------dev <br>
     * ------test <br>
     * ----member <br>
     * ------dev <br>
     * ------test <br>
     * ******************************************************************.
     */

    // zookeeper节点服务器地址
    // private static final String ZK_HOST = "dev3.mp2.com";
    private static final String ZK_HOST                 = "10.11.10.31";

    // 根节点地址
    /** The Constant ZOO_ROOT_DEV. */
    private static final String ZOO_ROOT_DEV            = "/mp2_dev";

    /** The Constant ZOO_ROOT_TEST. */
    private static final String ZOO_ROOT_TEST           = "/mp2_test";

    /** The Constant ZOO_ROOT_STAGING. */
    private static final String ZOO_ROOT_STAGING        = "/mp2_staging";

    /** The Constant ZOO_ROOT_PRODUCTION. */
    private static final String ZOO_ROOT_PRODUCTION     = "/mp2_production";

    // 二级节点：生产者/消费者
    /** The Constant ZOO_SECOND_COMMONS. */
    private static final String ZOO_SECOND_COMMONS      = "/commons";

    /** The Constant ZOO_SECOND_LIVECHAT. */
    private static final String ZOO_SECOND_LIVECHAT     = "/livechat";

    /** The Constant ZOO_SECOND_MANAGEMENT. */
    private static final String ZOO_SECOND_MANAGEMENT   = "/management";

    /** The Constant ZOO_SECOND_PROVIDER. */
    private static final String ZOO_SECOND_PROVIDER     = "/provider";

    /** The Constant ZOO_SECOND_CONSUMER. */
    private static final String ZOO_SECOND_CONSUMER     = "/consumer";

    // 三级节点： 工程名
    /** The Constant ZOO_THIRD_PLATFORM. */
    private static final String ZOO_THIRD_PLATFORM      = "/platform";

    /** The Constant ZOO_THIRD_TRADE. */
    private static final String ZOO_THIRD_TRADE         = "/trade";

    /** The Constant ZOO_THIRD_DATACENTER. */
    private static final String ZOO_THIRD_DATACENTER    = "/dataCenter";

    /** The Constant ZOO_THIRD_MEMBER. */
    private static final String ZOO_THIRD_MEMBER        = "/member";

    /** The Constant ZOO_THIRD_SMS. */
    private static final String ZOO_THIRD_SMS           = "/sms";

    /** The Constant ZOO_THIRD_MAINSITE. */
    private static final String ZOO_THIRD_MAINSITE      = "/mainsite";

    /** The Constant ZOO_THIRD_SHOP. */
    private static final String ZOO_THIRD_SHOP          = "/shop";

    /** The Constant ZOO_THIRD_PRODUCT. */
    private static final String ZOO_THIRD_PRODUCT       = "/product";

    /** The Constant ZOO_THIRD_WEB_MEMBER. */
    private static final String ZOO_THIRD_WEB_MEMBER    = "/member";

    /** The Constant ZOO_THIRD_SCHEDULED. */
    private static final String ZOO_THIRD_SCHEDULED     = "/schedule";

    /**
     * 递归删除dev节点.
     */
    @Test
    // @Ignore
    public void deleteDevPath(){
        ZookeeperUtil.deleteNode(ZK_HOST, ZOO_ROOT_DEV);
    }

    /**
     * 递归删除test节点.
     */
    @Test
    // @Ignore
    public void deleteTestPath(){
        ZookeeperUtil.deleteNode(ZK_HOST, ZOO_ROOT_TEST);
    }

    /**
     * Delete staging path.
     */
    @Test
    // @Ignore
    public void deleteStagingPath(){
        ZookeeperUtil.deleteNode(ZK_HOST, ZOO_ROOT_STAGING);
    }

    /**
     * Delete production path.
     */
    @Test
    // @Ignore
    public void deleteProductionPath(){
        ZookeeperUtil.deleteNode(ZK_HOST, ZOO_ROOT_PRODUCTION);
    }

    /**
     * ****************************************************************
     * 共通的配置信息
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-modules/mp2-member/dev/<br>
     * /mp2_dev/provider/member/dev<br>
     */
    @Test
    public void uploadCommonsDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_COMMONS + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_COMMONS;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-modules/mp2-member/test/<br>
     * /mp2_dev/provider/member/test<br>
     * .
     */
    @Test
    public void uploadCommonsTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_COMMONS + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_COMMONS;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload commons staging properties.
     */
    @Test
    public void uploadCommonsStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_COMMONS + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_COMMONS;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload commons production properties.
     */
    @Test
    public void uploadCommonsProductionProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_COMMONS + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_COMMONS;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * module-member 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-modules/mp2-member/dev/<br>
     * /mp2_dev/provider/member/dev<br>
     */
    @Test
    public void uploadMemberDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_MEMBER + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-modules/mp2-member/test/<br>
     * /mp2_dev/provider/member/test<br>
     * .
     */
    @Test
    public void uploadMemberTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_MEMBER + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload member staging properties.
     */
    @Test
    public void uploadMemberStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_MEMBER + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_PROVIDER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload member production properties.
     */
    @Test
    public void uploadMemberProductionProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_MEMBER + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_PROVIDER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * module-platform 工程
     * ****************************************************************.
     */

    /**
     * mp2-configuration/project/mp2-modules/mp2-platform/test/<br>
     * /mp2_dev/provider/platform/test<br>
     */
    @Test
    public void uploadPlatformDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PLATFORM + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_PLATFORM;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-modules/mp2-platform/test/<br>
     * /mp2_dev/provider/platform/test<br>
     * .
     */
    @Test
    public void uploadPlatformTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PLATFORM + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_PLATFORM;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload platform staging properties.
     */
    @Test
    public void uploadPlatformStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PLATFORM + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_PROVIDER + ZOO_THIRD_PLATFORM;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload platform production properties.
     */
    @Test
    public void uploadPlatformProductionProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PLATFORM + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_PROVIDER + ZOO_THIRD_PLATFORM;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * module-product 工程
     * ****************************************************************.
     */

    /**
     * mp2-configuration/project/mp2-modules/mp2-platform/test/<br>
     * /mp2_dev/provider/platform/test<br>
     */
    @Test
    public void uploadProductDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PRODUCT + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-modules/mp2-shop/test/<br>
     * /mp2_dev/provider/shop/test<br>
     * .
     */
    @Test
    public void uploadProductTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PRODUCT + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload product staging properties.
     */
    @Test
    public void uploadProductStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PRODUCT + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_PROVIDER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload product product properties.
     */
    @Test
    public void uploadProductProductProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_PRODUCT + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_PROVIDER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * module-trade 工程
     * ****************************************************************.
     */

    @Test
    public void uploadTradeDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_TRADE + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload trade test properties.
     */
    @Test
    public void uploadTradeTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_TRADE + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload trade staging properties.
     */
    @Test
    public void uploadTradeStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_TRADE + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_PROVIDER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload trade production properties.
     */
    @Test
    public void uploadTradeProductionProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_TRADE + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_PROVIDER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * modue-dataCenter 数据中心 工程
     * ****************************************************************.
     */

    @Test
    public void uploadDataCenterDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_DATACENTER + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_DATACENTER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload data center test properties.
     */
    @Test
    public void uploadDataCenterTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_DATACENTER + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_DATACENTER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload data center staging properties.
     */
    @Test
    public void uploadDataCenterStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_DATACENTER + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_PROVIDER + ZOO_THIRD_DATACENTER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload data center production properties.
     */
    @Test
    public void uploadDataCenterProductionProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_DATACENTER + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_PROVIDER + ZOO_THIRD_DATACENTER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * module-shop 工程
     * ****************************************************************.
     */

    /**
     * mp2-configuration/project/mp2-modules/mp2-shop/test/<br>
     * /mp2_dev/provider/shop/test<br>
     */
    @Test
    public void uploadShopDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_SHOP + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_SHOP;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-modules/mp2-shop/test/<br>
     * /mp2_dev/provider/shop/test<br>
     * .
     */
    @Test
    public void uploadShopTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_SHOP + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_SHOP;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload sms properties.
     */
    @Test
    public void uploadSMSProperties(){
        String localPropertiesUrl1 = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_SMS + FOURTH_DEV;
        String zooPath1 = ZOO_ROOT_DEV + ZOO_SECOND_PROVIDER + ZOO_THIRD_SMS;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl1, zooPath1);

        String localPropertiesUrl2 = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_SMS + FOURTH_TEST;
        String zooPath2 = ZOO_ROOT_TEST + ZOO_SECOND_PROVIDER + ZOO_THIRD_SMS;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl2, zooPath2);
    }

    /**
     * Upload shop staging properties.
     */
    @Test
    public void uploadShopStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_MODULES + THIRD_MODULE_SHOP + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_PROVIDER + ZOO_THIRD_SHOP;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-mainsite 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web/mp2-web-marketplace/mp2-web-marketplace-mainsite/dev<br>
     * /mp2_dev/consumer/mainsite/dev<br>
     */
    @Test
    public void uploadWebMainsiteDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MAINSITE + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_THIRD_MAINSITE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-web//mp2-web-marketplace/mp2-web-marketplace-mainsite/test<br>
     * /mp2_dev/consumer/mainsite/test<br>
     * .
     */
    @Test
    public void uploadWebMainsiteTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MAINSITE + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_THIRD_MAINSITE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web mainsite staging properties.
     */
    @Test
    public void uploadWebMainsiteStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MAINSITE + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_CONSUMER + ZOO_THIRD_MAINSITE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-member 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web/mp2-web-marketplace/mp2-web-marketplace-member/dev<br>
     * /mp2_dev/consumer/mainsite/dev<br>
     */
    @Test
    public void uploadWebMemberDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MEMBER + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-web//mp2-web-marketplace/mp2-web-marketplace-member/test<br>
     * /mp2_dev/consumer/mainsite/test<br>
     * .
     */
    @Test
    public void uploadWebMemberTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MEMBER + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web member staging properties.
     */
    @Test
    public void uploadWebMemberStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MEMBER + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_CONSUMER + ZOO_THIRD_MEMBER;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-shop 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web//mp2-web-marketplace/mp2-web-marketplace-member/dev<br>
     * /mp2_dev/provider/platform/dev<br>
     */
    @Test
    public void uploadWebShopDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_SHOP + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_THIRD_SHOP;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-web//mp2-web-marketplace/mp2-web-marketplace-member/test<br>
     * /mp2_dev/provider/platform/test<br>
     * .
     */
    @Test
    public void uploadWebShopTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_SHOP + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_THIRD_SHOP;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web shop staging properties.
     */
    @Test
    public void uploadWebShopStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_SHOP + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_CONSUMER + ZOO_THIRD_SHOP;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-management 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web//mp2-web-managementsystem/dev<br>
     * /mp2_dev/management/dev<br>
     */
    @Test
    public void uploadWebManagementDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MANAGEMENT + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_SECOND_MANAGEMENT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-web/mp2-web-managementsystem/test<br>
     * /mp2_dev/management/test<br>
     * .
     */
    @Test
    public void uploadWebManagementTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MANAGEMENT + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_SECOND_MANAGEMENT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web management staging properties.
     */
    @Test
    public void uploadWebManagementStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_MANAGEMENT + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_CONSUMER + ZOO_SECOND_MANAGEMENT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-livechat 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web/mp2-livechat/test<br>
     * /mp2_dev/livechat/test<br>
     */
    @Test
    public void uploadWebLivechatDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_LIVECHAT + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_LIVECHAT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * mp2-configuration/project/mp2-web/mp2-livechat/test<br>
     * /mp2_dev/livechat/test<br>
     * .
     */
    @Test
    public void uploadWebLivechatTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_LIVECHAT + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_LIVECHAT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web livechat staging properties.
     */
    @Test
    public void uploadWebLivechatStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_LIVECHAT + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_LIVECHAT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-mainsite 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web/mp2-web-marketplace/mp2-web-marketplace-product/dev<br>
     * /mp2_dev/consumer/product/dev<br>
     */
    @Test
    public void uploadWebProductDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_PRODUCT + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web product test properties.
     */
    @Test
    public void uploadWebProductTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_PRODUCT + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web product staging properties.
     */
    @Test
    public void uploadWebProductStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_PRODUCT + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_CONSUMER + ZOO_THIRD_PRODUCT;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * ****************************************************************
     * web-member 工程
     * ****************************************************************.
     */
    /**
     * mp2-configuration/project/mp2-web/mp2-web-marketplace/mp2-web-marketplace-member/dev<br>
     * /mp2_dev/consumer/mainsite/dev<br>
     */
    @Test
    public void uploadWebTradeDevProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_TRADE + FOURTH_DEV;
        String zooPath = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web trade test properties.
     */
    @Test
    public void uploadWebTradeTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_TRADE + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload web trade staging properties.
     */
    @Test
    public void uploadWebTradeStagingProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_WEB + THIRD_WEB_TRADE + FOURTH_STAGING;
        String zooPath = ZOO_ROOT_STAGING + ZOO_SECOND_CONSUMER + ZOO_THIRD_TRADE;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload scheduled dev properties.
     */
    @Test
    public void uploadScheduledDevProperties(){
        String localPropertiesUrl1 = LOCAL_PLATFORM_URL + SECOND_MP2_SCHEDULED + FOURTH_DEV;
        String zooPath1 = ZOO_ROOT_DEV + ZOO_SECOND_CONSUMER + ZOO_THIRD_SCHEDULED;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl1, zooPath1);
    }

    /**
     * Upload scheduled test properties.
     */
    @Test
    public void uploadScheduledTestProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_SCHEDULED + FOURTH_TEST;
        String zooPath = ZOO_ROOT_TEST + ZOO_SECOND_CONSUMER + ZOO_THIRD_SCHEDULED;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

    /**
     * Upload scheduled production properties.
     */
    @Test
    public void uploadScheduledProductionProperties(){
        String localPropertiesUrl = LOCAL_PLATFORM_URL + SECOND_MP2_SCHEDULED + FOURTH_PRODUCTION;
        String zooPath = ZOO_ROOT_PRODUCTION + ZOO_SECOND_CONSUMER + ZOO_THIRD_SCHEDULED;

        ZookeeperUtil.uploadAllProperties(ZK_HOST, localPropertiesUrl, zooPath);
    }

}
