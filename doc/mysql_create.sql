
-- ----------------------------
-- Table structure for dw_account
-- ----------------------------

DROP TABLE IF EXISTS `dw_account`;
CREATE TABLE `dw_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `total` decimal(50,6) DEFAULT '0.000000' COMMENT '资金总额',
  `use_money` decimal(50,6) DEFAULT '0.000000',
  `no_use_money` decimal(50,6) DEFAULT '0.000000',
  `collection` decimal(50,6) DEFAULT '0.000000',
  `hongbao` decimal(20,6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1962 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_account_bank
-- ----------------------------
DROP TABLE IF EXISTS `dw_account_bank`;
CREATE TABLE `dw_account_bank` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `account` varchar(100) DEFAULT NULL COMMENT '账号',
  `bank` varchar(50) DEFAULT NULL COMMENT '所属银行',
  `branch` varchar(100) DEFAULT NULL COMMENT '支行',
  `province` int(5) DEFAULT '0' COMMENT '省份',
  `city` int(5) DEFAULT '0' COMMENT '城市',
  `area` int(5) DEFAULT '0' COMMENT '区',
  `addtime` varchar(11) DEFAULT NULL,
  `addip` varchar(15) DEFAULT NULL,
  `modify_username` varchar(30) DEFAULT NULL,
  `bank_realname` varchar(50) DEFAULT NULL COMMENT '账户人',
  `payment` int(10) DEFAULT NULL COMMENT '银行代表参数',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=utf8 COMMENT='银行帐户';



-- ----------------------------
-- Table structure for dw_account_cash
-- ----------------------------
DROP TABLE IF EXISTS `dw_account_cash`;
CREATE TABLE `dw_account_cash` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `account` varchar(50) DEFAULT '0' COMMENT '账号',
  `bank` varchar(302) DEFAULT NULL COMMENT '所属银行',
  `branch` varchar(100) DEFAULT NULL COMMENT '支行',
  `total` varchar(20) DEFAULT '0' COMMENT 'ܶ',
  `credited` varchar(20) DEFAULT '0' COMMENT '到账总额',
  `fee` varchar(20) DEFAULT '0' COMMENT '手续费',
  `verify_userid` int(11) DEFAULT NULL,
  `verify_time` int(11) DEFAULT NULL,
  `verify_remark` varchar(250) DEFAULT NULL,
  `addtime` varchar(11) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `hongbao` decimal(20,6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_ids` (`user_id`,`status`) USING BTREE,
  KEY `user_idv` (`user_id`,`status`,`verify_userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1259 DEFAULT CHARSET=utf8 COMMENT='提现记录';



-- ----------------------------
-- Table structure for dw_account_log
-- ----------------------------
DROP TABLE IF EXISTS `dw_account_log`;
CREATE TABLE `dw_account_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  `total` decimal(20,6) DEFAULT '0.000000',
  `money` decimal(20,6) DEFAULT NULL COMMENT '金额',
  `use_money` decimal(20,6) DEFAULT '0.000000' COMMENT '可用金额',
  `no_use_money` decimal(20,6) DEFAULT '0.000000' COMMENT '冻结金额',
  `collection` decimal(20,6) DEFAULT '0.000000',
  `to_user` int(11) DEFAULT NULL COMMENT '交易对方',
  `remark` varchar(250) DEFAULT '0' COMMENT '备注',
  `addtime` varchar(11) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57169 DEFAULT CHARSET=utf8 COMMENT='资金记录';



-- ----------------------------
-- Table structure for dw_account_payment
-- ----------------------------
DROP TABLE IF EXISTS `dw_account_payment`;
CREATE TABLE `dw_account_payment` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `nid` varchar(100) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `style` int(2) DEFAULT NULL,
  `config` longtext,
  `fee` int(10) DEFAULT '0',
  `fee_type` int(2) DEFAULT NULL,
  `max_money` int(10) DEFAULT NULL,
  `max_fee` int(10) DEFAULT NULL,
  `description` longtext,
  `order` smallint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dw_account_recharge
-- ----------------------------
DROP TABLE IF EXISTS `dw_account_recharge`;
CREATE TABLE `dw_account_recharge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `trade_no` varchar(20) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `money` decimal(20,6) DEFAULT '0.000000' COMMENT '金额',
  `payment` varchar(100) DEFAULT NULL COMMENT '所属银行',
  `return` text,
  `type` varchar(10) DEFAULT '0' COMMENT '类型',
  `remark` varchar(250) DEFAULT '0' COMMENT '备注',
  `fee` varchar(10) DEFAULT NULL,
  `verify_userid` int(11) DEFAULT '0' COMMENT '审核人',
  `verify_time` varchar(11) DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(250) DEFAULT '' COMMENT '审核备注',
  `addtime` varchar(11) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `recharge_kefuid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_ids` (`user_id`,`status`) USING BTREE,
  KEY `user_idp` (`user_id`,`payment`) USING BTREE,
  KEY `user_idv` (`user_id`,`payment`,`verify_userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7471 DEFAULT CHARSET=utf8 COMMENT='充值记录';


-- ----------------------------
-- Table structure for dw_ad
-- ----------------------------
DROP TABLE IF EXISTS `dw_ad`;
CREATE TABLE `dw_ad` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(20) NOT NULL,
  `order` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `litpic` varchar(100) NOT NULL,
  `timelimit` int(2) NOT NULL DEFAULT '0',
  `firsttime` varchar(20) NOT NULL DEFAULT '',
  `endtime` varchar(20) NOT NULL DEFAULT '',
  `content` text NOT NULL,
  `endcontent` text NOT NULL,
  `addtime` varchar(50) NOT NULL,
  `addip` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_area
-- ----------------------------
DROP TABLE IF EXISTS `dw_area`;
CREATE TABLE `dw_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `domain` varchar(100) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nid` (`nid`) USING BTREE,
  KEY `nidp` (`nid`,`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3577 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_article
-- ----------------------------
DROP TABLE IF EXISTS `dw_article`;
CREATE TABLE `dw_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `littitle` varchar(200) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `litpic` varchar(255) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `publish` varchar(50) DEFAULT NULL,
  `is_jump` varchar(1) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `jumpurl` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `content` text,
  `order` int(11) DEFAULT '0',
  `hits` int(11) DEFAULT '0',
  `comment` int(11) DEFAULT '0',
  `is_comment` varchar(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_article_fields
-- ----------------------------
DROP TABLE IF EXISTS `dw_article_fields`;
CREATE TABLE `dw_article_fields` (
  `aid` int(11) unsigned NOT NULL DEFAULT '0',
  `files` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_attestation
-- ----------------------------
DROP TABLE IF EXISTS `dw_attestation`;
CREATE TABLE `dw_attestation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `type_id` int(11) DEFAULT '0' COMMENT '上传的类型',
  `name` varchar(255) DEFAULT NULL,
  `status` int(2) DEFAULT '0' COMMENT '认证的状态',
  `litpic` varchar(255) DEFAULT NULL COMMENT '认证的图片',
  `content` varchar(255) DEFAULT NULL COMMENT '认证的简介',
  `jifen` int(20) DEFAULT '0' COMMENT '认证的积分',
  `pic` text,
  `pic2` varchar(100) DEFAULT NULL,
  `pic3` varchar(100) DEFAULT NULL,
  `verify_time` varchar(32) DEFAULT NULL COMMENT '审核时间',
  `verify_user` int(11) DEFAULT NULL COMMENT '审核人',
  `verify_remark` varchar(250) DEFAULT NULL COMMENT '审核备注',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2522 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_attestation_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_attestation_type`;
CREATE TABLE `dw_attestation_type` (
  `type_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '类型名称',
  `order` varchar(10) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `jifen` int(20) DEFAULT '0' COMMENT '积分',
  `summary` varchar(200) DEFAULT NULL COMMENT '简介',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_autotenderorder
-- ----------------------------
DROP TABLE IF EXISTS `dw_autotenderorder`;
CREATE TABLE `dw_autotenderorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `User_id` int(11) DEFAULT '0',
  `User_name` varchar(50) DEFAULT NULL,
  `Auto_order` int(11) DEFAULT '0',
  `Auto_score` int(11) DEFAULT '0',
  `User_useMoney` decimal(11,2) DEFAULT NULL,
  `User_useMoneyOrder` int(11) DEFAULT '0',
  `User_autoMoney` decimal(11,2) DEFAULT NULL,
  `User_autoMoneyOrder` int(11) DEFAULT '0',
  `User_jifen` int(11) DEFAULT '0',
  `User_jifenOrder` int(11) DEFAULT '0',
  `Auto_lasttime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_award_payments
-- ----------------------------
DROP TABLE IF EXISTS `dw_award_payments`;
CREATE TABLE `dw_award_payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `money` decimal(20,6) DEFAULT NULL COMMENT '进入奖金池的金额',
  `status` int(2) DEFAULT NULL COMMENT '收支方式 0：收入 1：支出',
  `addtime` varchar(50) DEFAULT NULL,
  `award_money` decimal(20,6) DEFAULT NULL COMMENT '奖励金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖励收支表';



-- ----------------------------
-- Table structure for dw_bonus_apr
-- ----------------------------
DROP TABLE IF EXISTS `dw_bonus_apr`;
CREATE TABLE `dw_bonus_apr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `borrow_id` int(11) DEFAULT NULL,
  `order` int(3) DEFAULT NULL,
  `apr` decimal(11,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_borrow
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow`;
CREATE TABLE `dw_borrow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `flag` varchar(50) DEFAULT NULL COMMENT '属性',
  `is_vouch` int(2) NOT NULL,
  `type` varchar(50) NOT NULL,
  `view_type` int(11) NOT NULL,
  `vouch_award` varchar(40) NOT NULL,
  `vouch_user` varchar(100) NOT NULL,
  `vouch_account` varchar(50) NOT NULL,
  `vouch_times` int(11) NOT NULL DEFAULT '0',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `publish` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `customer` varchar(11) DEFAULT NULL COMMENT '客服',
  `number_id` varchar(50) DEFAULT NULL,
  `verify_user` varchar(11) DEFAULT NULL COMMENT '审核人',
  `verify_time` varchar(50) DEFAULT NULL COMMENT '客服',
  `verify_remark` varchar(255) DEFAULT NULL,
  `repayment_user` int(11) DEFAULT '0',
  `forst_account` varchar(50) DEFAULT '0',
  `repayment_account` varchar(50) DEFAULT NULL,
  `monthly_repayment` varchar(50) DEFAULT NULL COMMENT '每月还款额',
  `repayment_yesaccount` varchar(50) DEFAULT '0',
  `repayment_yesinterest` varchar(50) NOT NULL,
  `repayment_time` varchar(50) DEFAULT NULL,
  `repayment_remark` varchar(250) DEFAULT NULL,
  `success_time` varchar(50) DEFAULT NULL,
  `end_time` varchar(50) DEFAULT NULL,
  `payment_account` varchar(50) DEFAULT NULL,
  `each_time` varchar(50) DEFAULT NULL,
  `use` varchar(50) DEFAULT NULL COMMENT '用途',
  `time_limit` varchar(50) DEFAULT NULL COMMENT '借款期限',
  `style` varchar(50) DEFAULT NULL COMMENT '还款方式',
  `account` varchar(50) DEFAULT NULL COMMENT '借贷总金额',
  `account_yes` varchar(50) DEFAULT '0',
  `tender_times` varchar(11) DEFAULT '0',
  `apr` decimal(18,6) DEFAULT NULL COMMENT '年利率',
  `lowest_account` varchar(50) DEFAULT NULL COMMENT '最低投标金额',
  `most_account` varchar(50) DEFAULT NULL COMMENT '最多投标总额',
  `valid_time` varchar(50) DEFAULT NULL COMMENT '有效时间',
  `award` int(50) DEFAULT NULL COMMENT '投标奖励',
  `part_account` decimal(18,6) DEFAULT NULL COMMENT '分摊奖励金额',
  `funds` decimal(18,6) DEFAULT NULL COMMENT '比例奖励的比例',
  `is_false` varchar(50) DEFAULT NULL COMMENT '标的失败时也同样奖励 ',
  `open_account` varchar(50) DEFAULT NULL COMMENT '公开我的帐户资金情况',
  `open_borrow` varchar(50) DEFAULT NULL COMMENT '公开我的借款资金情况',
  `open_tender` varchar(50) DEFAULT NULL COMMENT '公开我的投标资金情况',
  `open_credit` varchar(50) DEFAULT NULL COMMENT '公开我的信用额度情况',
  `content` text COMMENT '详细说明',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `is_mb` int(1) NOT NULL DEFAULT '0',
  `is_fast` int(11) NOT NULL DEFAULT '0',
  `is_jin` int(1) DEFAULT '0',
  `is_xin` int(1) DEFAULT NULL,
  `pwd` varchar(50) DEFAULT NULL,
  `isday` int(1) DEFAULT NULL,
  `time_limit_day` int(2) DEFAULT NULL,
  `is_art` int(1) DEFAULT NULL,
  `is_charity` int(1) DEFAULT NULL,
  `is_project` int(1) DEFAULT NULL,
  `is_flow` int(1) DEFAULT NULL COMMENT '流转标的标识字段',
  `flow_status` int(1) DEFAULT '0' COMMENT '流转标的状态',
  `flow_money` int(11) DEFAULT NULL COMMENT '流转标',
  `flow_count` int(5) DEFAULT NULL COMMENT '流转标的总份数',
  `flow_yescount` int(5) DEFAULT NULL COMMENT '流转标已经购买的份数',
  `is_student` int(1) DEFAULT NULL COMMENT '学信标字段',
  `is_offvouch` int(1) DEFAULT NULL COMMENT '线下担保标字段',
  `is_recommend` int(5) DEFAULT NULL,
  `late_award` decimal(5,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_ids` (`user_id`,`status`) USING BTREE,
  KEY `user_idst` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1018 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dw_borrow_amountlog
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_amountlog`;
CREATE TABLE `dw_borrow_amountlog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `amount_type` varchar(50) NOT NULL,
  `account` decimal(20,6) NOT NULL,
  `account_total` decimal(20,6) NOT NULL,
  `account_use` decimal(20,6) NOT NULL,
  `account_nouse` decimal(20,6) NOT NULL,
  `remark` varchar(250) CHARACTER SET gbk NOT NULL,
  `addtime` varchar(50) NOT NULL,
  `addip` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_auto
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_auto`;
CREATE TABLE `dw_borrow_auto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL DEFAULT '0',
  `tender_type` int(11) NOT NULL DEFAULT '1',
  `tender_account` int(11) NOT NULL DEFAULT '0',
  `tender_scale` int(11) NOT NULL DEFAULT '0',
  `video_status` int(11) NOT NULL DEFAULT '0',
  `scene_status` int(11) NOT NULL DEFAULT '0',
  `my_friend` int(11) NOT NULL DEFAULT '0',
  `not_black` int(11) NOT NULL DEFAULT '0',
  `late_status` int(11) NOT NULL DEFAULT '0',
  `dianfu_status` int(11) NOT NULL DEFAULT '0',
  `black_status` int(11) NOT NULL DEFAULT '0',
  `late_times` int(11) NOT NULL DEFAULT '0',
  `dianfu_times` int(11) NOT NULL DEFAULT '0',
  `black_user` int(11) NOT NULL DEFAULT '0',
  `black_times` int(11) NOT NULL DEFAULT '0',
  `not_late_black` int(11) NOT NULL DEFAULT '0',
  `borrow_credit_status` int(11) NOT NULL DEFAULT '0',
  `borrow_credit_first` int(11) DEFAULT NULL,
  `borrow_credit_last` int(11) DEFAULT NULL,
  `borrow_style_status` int(11) NOT NULL DEFAULT '0',
  `borrow_style` int(11) DEFAULT NULL,
  `timelimit_status` int(11) NOT NULL DEFAULT '0',
  `timelimit_month_first` int(11) NOT NULL DEFAULT '0',
  `timelimit_month_last` int(11) NOT NULL DEFAULT '0',
  `apr_status` int(11) NOT NULL DEFAULT '0',
  `apr_first` int(11) DEFAULT NULL,
  `apr_last` int(11) DEFAULT NULL,
  `award_status` int(11) NOT NULL DEFAULT '0',
  `award_first` float DEFAULT NULL,
  `award_last` float DEFAULT NULL,
  `vouch_status` int(11) NOT NULL DEFAULT '0',
  `tuijian_status` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `addtime` int(11) NOT NULL DEFAULT '0',
  `fast_status` int(11) DEFAULT NULL,
  `xin_status` int(11) DEFAULT NULL,
  `jin_status` int(11) DEFAULT NULL,
  `timelimit_day_first` int(11) DEFAULT NULL,
  `timelimit_day_last` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_collection
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_collection`;
CREATE TABLE `dw_borrow_collection` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `status` int(2) DEFAULT '0',
  `order` int(2) DEFAULT NULL,
  `tender_id` int(11) DEFAULT '0' COMMENT '借款id',
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间',
  `repay_yestime` varchar(50) DEFAULT NULL COMMENT '已经还款时间',
  `repay_account` varchar(50) DEFAULT '0' COMMENT '预还金额',
  `repay_yesaccount` varchar(50) DEFAULT '0' COMMENT '实还金额',
  `interest` varchar(50) DEFAULT '0',
  `capital` varchar(50) DEFAULT '0',
  `late_days` int(11) NOT NULL DEFAULT '0',
  `late_interest` varchar(11) NOT NULL DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `bonus` decimal(50,6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9256 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_config
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_config`;
CREATE TABLE `dw_borrow_config` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `most_account` decimal(10,4) DEFAULT '0.0000',
  `lowest_account` decimal(10,4) DEFAULT '0.0000',
  `most_apr` decimal(10,4) DEFAULT '0.0000',
  `lowest_apr` decimal(10,4) DEFAULT '0.0000',
  `most_award_apr` decimal(10,4) DEFAULT '0.0000',
  `lowest_award_apr` decimal(10,4) DEFAULT '0.0000',
  `most_award_funds` decimal(10,4) DEFAULT '0.0000',
  `lowest_award_funds` decimal(10,4) DEFAULT '0.0000',
  `is_trail` int(1) DEFAULT '0',
  `is_review` int(1) DEFAULT '0',
  `identify` varchar(30) DEFAULT '111111',
  `remark` varchar(300) DEFAULT NULL,
  `managefee` decimal(10,4) NOT NULL COMMENT '月标管理费',
  `daymanagefee` decimal(10,4) NOT NULL COMMENT '天标管理费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_line
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_line`;
CREATE TABLE `dw_borrow_line` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `flag` varchar(50) DEFAULT NULL COMMENT '属性',
  `type` int(2) DEFAULT '0' COMMENT '借款类型',
  `borrow_use` int(10) DEFAULT '0' COMMENT '贷款用途',
  `borrow_qixian` int(10) DEFAULT '0' COMMENT '贷款期限',
  `province` int(10) DEFAULT '0' COMMENT '省份',
  `city` int(10) DEFAULT '0' COMMENT '城市',
  `area` int(10) DEFAULT '0' COMMENT '地区',
  `account` varchar(11) DEFAULT NULL COMMENT '贷款金额',
  `content` text COMMENT '详细说明',
  `pawn` varchar(2) DEFAULT NULL COMMENT '有无抵押',
  `tel` varchar(15) DEFAULT NULL COMMENT '电话',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `xing` varchar(11) DEFAULT NULL COMMENT '姓',
  `verify_user` int(11) DEFAULT NULL COMMENT '审核人',
  `verify_time` varchar(50) DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(255) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dw_borrow_repayment
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_repayment`;
CREATE TABLE `dw_borrow_repayment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `status` int(2) DEFAULT '0',
  `webstatus` int(2) NOT NULL DEFAULT '0' COMMENT '网站代还',
  `order` int(2) DEFAULT NULL,
  `borrow_id` int(11) DEFAULT '0' COMMENT '借款id',
  `repayment_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间',
  `repayment_yestime` varchar(50) DEFAULT NULL COMMENT '已经还款时间',
  `repayment_account` varchar(50) DEFAULT '0' COMMENT '预还金额',
  `repayment_yesaccount` varchar(50) DEFAULT '0' COMMENT '实还金额',
  `late_days` int(11) NOT NULL DEFAULT '0',
  `late_interest` varchar(11) NOT NULL DEFAULT '0',
  `interest` varchar(50) DEFAULT '0',
  `capital` varchar(50) DEFAULT '0',
  `forfeit` varchar(50) DEFAULT '0' COMMENT '滞纳金',
  `reminder_fee` varchar(50) DEFAULT '0' COMMENT '崔收费',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `bonus` decimal(50,6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idb` (`borrow_id`) USING BTREE,
  KEY `user_idubs` (`borrow_id`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1132 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_tender
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_tender`;
CREATE TABLE `dw_borrow_tender` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `borrow_id` int(11) DEFAULT '0',
  `money` varchar(50) DEFAULT NULL,
  `account` varchar(50) DEFAULT '0',
  `repayment_account` varchar(50) DEFAULT '0' COMMENT '总额',
  `interest` varchar(50) NOT NULL DEFAULT '0',
  `repayment_yesaccount` varchar(50) DEFAULT '0' COMMENT '已还总额',
  `wait_account` varchar(50) DEFAULT '0' COMMENT '待还总额',
  `wait_interest` varchar(50) DEFAULT '0' COMMENT '待还利息',
  `repayment_yesinterest` varchar(50) DEFAULT '0' COMMENT '已还利息',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `auto_repurchase` int(1) DEFAULT NULL COMMENT '投标者是否自动续转',
  `is_auto_tender` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_idb` (`borrow_id`) USING BTREE,
  KEY `user_idub` (`user_id`,`borrow_id`) USING BTREE,
  KEY `user_idubs` (`user_id`,`borrow_id`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7011 DEFAULT CHARSET=utf8 COMMENT='招标';


-- ----------------------------
-- Table structure for dw_borrow_union
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_union`;
CREATE TABLE `dw_borrow_union` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `range` varchar(255) DEFAULT NULL COMMENT '经营范围',
  `content` text COMMENT '公司简介',
  `verify_remark` varchar(255) DEFAULT NULL,
  `verify_time` varchar(10) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_borrow_vouch
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_vouch`;
CREATE TABLE `dw_borrow_vouch` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `borrow_id` int(11) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `account` varchar(20) NOT NULL,
  `vouch_account` decimal(11,6) NOT NULL,
  `content` text CHARACTER SET gbk NOT NULL,
  `award_fund` varchar(10) CHARACTER SET gbk NOT NULL,
  `award_account` decimal(11,6) NOT NULL,
  `addtime` varchar(50) NOT NULL,
  `addip` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_vouch_collection
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_vouch_collection`;
CREATE TABLE `dw_borrow_vouch_collection` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT '0',
  `user_id` int(11) NOT NULL,
  `borrow_id` int(11) NOT NULL,
  `order` int(2) DEFAULT NULL,
  `vouch_id` int(11) DEFAULT '0' COMMENT '借款id',
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间',
  `repay_yestime` varchar(50) DEFAULT NULL COMMENT '已经还款时间',
  `repay_account` varchar(50) DEFAULT '0' COMMENT '预还金额',
  `repay_yesaccount` varchar(50) DEFAULT '0' COMMENT '实还金额',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_borrow_vouch_repayment
-- ----------------------------
DROP TABLE IF EXISTS `dw_borrow_vouch_repayment`;
CREATE TABLE `dw_borrow_vouch_repayment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT '0',
  `user_id` int(11) NOT NULL,
  `borrow_id` int(11) NOT NULL,
  `order` int(2) DEFAULT NULL,
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间',
  `repay_yestime` varchar(50) DEFAULT NULL COMMENT '已经还款时间',
  `repay_account` varchar(50) DEFAULT '0' COMMENT '预还金额',
  `repay_yesaccount` varchar(50) DEFAULT '0' COMMENT '实还金额',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_cache
-- ----------------------------
DROP TABLE IF EXISTS `dw_cache`;
CREATE TABLE `dw_cache` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(20) DEFAULT NULL,
  `user_num` int(11) DEFAULT NULL,
  `user_online_num` int(11) DEFAULT '0',
  `user_online_time` varchar(30) DEFAULT NULL,
  `last_user` varchar(20) DEFAULT NULL,
  `bbs_first_visit` int(20) DEFAULT '0',
  `bbs_topics_num` int(11) DEFAULT NULL,
  `bbs_posts_num` int(11) DEFAULT NULL,
  `bbs_today_topics` int(11) DEFAULT NULL,
  `bbs_today_posts` int(11) DEFAULT NULL,
  `bbs_yesterday_topics` int(11) DEFAULT NULL,
  `bbs_yesterday_posts` int(11) DEFAULT NULL,
  `bbs_most_topics` int(11) DEFAULT NULL,
  `bbs_most_posts` int(11) DEFAULT NULL,
  `borrow_account` varchar(11) DEFAULT '0',
  `borrow_success` varchar(20) DEFAULT '0',
  `borrow_num` int(10) DEFAULT '0',
  `borrow_successnum` varchar(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_cms_article
-- ----------------------------
DROP TABLE IF EXISTS `dw_cms_article`;
CREATE TABLE `dw_cms_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0',
  `title` varchar(255) DEFAULT NULL,
  `littitle` varchar(250) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `litpic` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `is_jump` int(1) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `jumpurl` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `content` text,
  `order` int(11) DEFAULT '0',
  `hits` int(11) DEFAULT '0',
  `comment` int(11) DEFAULT '0',
  `is_comment` int(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_cms_article_fields
-- ----------------------------
DROP TABLE IF EXISTS `dw_cms_article_fields`;
CREATE TABLE `dw_cms_article_fields` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_comment
-- ----------------------------
DROP TABLE IF EXISTS `dw_comment`;
CREATE TABLE `dw_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  `module_code` varchar(50) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
  `comment` text,
  `flag` varchar(30) DEFAULT NULL COMMENT '定义属性',
  `order` varchar(10) DEFAULT NULL COMMENT '排序',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `addtime` varchar(30) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_cooperation_login
-- ----------------------------
DROP TABLE IF EXISTS `dw_cooperation_login`;
CREATE TABLE `dw_cooperation_login` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` tinyint(2) DEFAULT NULL COMMENT '合作登陆类型:1QQ,2sina',
  `user_id` int(10) DEFAULT NULL COMMENT '会员编号',
  `open_id` varchar(200) DEFAULT NULL COMMENT '外部ID',
  `open_key` varchar(200) NOT NULL COMMENT '外部Key',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作登录表';



-- ----------------------------
-- Table structure for dw_credit
-- ----------------------------
DROP TABLE IF EXISTS `dw_credit`;
CREATE TABLE `dw_credit` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '会员ID',
  `value` int(11) DEFAULT '0' COMMENT '积分数值',
  `op_user` int(11) DEFAULT NULL COMMENT '操作者',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加IP',
  `updatetime` varchar(11) DEFAULT NULL COMMENT '最后更新时间',
  `updateip` varchar(30) DEFAULT NULL COMMENT '最后更新ID',
  `tender_value` int(11) DEFAULT NULL,
  `borrow_value` int(11) DEFAULT NULL COMMENT '借款积分',
  `gift_value` int(11) DEFAULT NULL COMMENT '赠送积分',
  `expense_value` int(11) DEFAULT NULL COMMENT '消费积分',
  `valid_value` int(11) DEFAULT NULL COMMENT '有效积分',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员积分';


-- ----------------------------
-- Table structure for dw_credit_log
-- ----------------------------
DROP TABLE IF EXISTS `dw_credit_log`;
CREATE TABLE `dw_credit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `type_id` int(11) DEFAULT '0' COMMENT '积分类型ID',
  `op` tinyint(1) DEFAULT '1' COMMENT '变动类型,1:增加,2:减少',
  `value` int(11) DEFAULT '0' COMMENT '变动积分数值',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `op_user` int(11) DEFAULT NULL COMMENT '操作者',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3404 DEFAULT CHARSET=utf8 COMMENT='会员积分日志';


-- ----------------------------
-- Table structure for dw_credit_rank
-- ----------------------------
DROP TABLE IF EXISTS `dw_credit_rank`;
CREATE TABLE `dw_credit_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '等级名称',
  `rank` int(11) DEFAULT '0' COMMENT '等级',
  `point1` int(11) DEFAULT '0' COMMENT '开始分值',
  `point2` int(11) DEFAULT '0' COMMENT '最后分值',
  `pic` varchar(100) DEFAULT NULL COMMENT '图片',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='会员积分日志';


-- ----------------------------
-- Table structure for dw_credit_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_credit_type`;
CREATE TABLE `dw_credit_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '积分名称',
  `nid` varchar(50) DEFAULT NULL COMMENT '积分代码',
  `value` int(11) DEFAULT '0' COMMENT '积分数值',
  `cycle` tinyint(1) DEFAULT '2' COMMENT '积分周期，1:一次,2:每天,3:间隔分钟,4:不限',
  `award_times` tinyint(4) DEFAULT NULL COMMENT '奖励次数,0:不限',
  `interval` int(11) DEFAULT '1' COMMENT '时间间隔，单位分钟',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `op_user` int(11) DEFAULT NULL COMMENT '操作者',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加IP',
  `updatetime` int(11) DEFAULT NULL COMMENT '最后更新时间',
  `updateip` varchar(30) DEFAULT NULL COMMENT '最后更新ID',
  `rule_nid` varchar(50) DEFAULT NULL COMMENT '规则NID',
  `credit_category` tinyint(2) DEFAULT NULL COMMENT '积分种类:1投资积分,2借款积分,3赠送积分,4消费积分',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ct_nid` (`nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='积分类型';



-- ----------------------------
-- Table structure for dw_daizi
-- ----------------------------
DROP TABLE IF EXISTS `dw_daizi`;
CREATE TABLE `dw_daizi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isqiye` int(1) NOT NULL,
  `yongtu` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `Mortgage` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `diya` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `ddlAge` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `ddlOccupation` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `ddlInCome` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `ddlIndustry` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `ddlTurnover` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `ddlStaff` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `txtRemark` text CHARACTER SET gb2312 NOT NULL,
  `txtContact` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `Sex` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `txtTelephone` varchar(200) CHARACTER SET gb2312 NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `borrow_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_donate_payments
-- ----------------------------
DROP TABLE IF EXISTS `dw_donate_payments`;
CREATE TABLE `dw_donate_payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `money` decimal(20,6) DEFAULT NULL,
  `borrow_time` varchar(50) DEFAULT NULL COMMENT '借款日期',
  `borrow_use` varchar(1000) DEFAULT NULL COMMENT '用途',
  `repayment_time` varchar(50) DEFAULT NULL COMMENT '约定还款日期',
  `borrow_content` varchar(1000) DEFAULT NULL COMMENT '借款情况',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `status` int(4) DEFAULT NULL COMMENT '收支方式 0：收入 1：支出',
  `addtime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_donate_record
-- ----------------------------
DROP TABLE IF EXISTS `dw_donate_record`;
CREATE TABLE `dw_donate_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `money` decimal(20,6) DEFAULT NULL COMMENT '捐赠金额',
  `giving_way` int(8) DEFAULT NULL COMMENT '捐赠方式  0：否 1：是',
  `display_way` int(8) DEFAULT NULL COMMENT '显示方式0：匿名1：显示用户名2：显示真实姓名3：显示用户名和真实姓名',
  `remark` varchar(1000) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `award_money` decimal(20,6) DEFAULT NULL COMMENT '奖励金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基金收支表';



-- ----------------------------
-- Table structure for dw_editor
-- ----------------------------
DROP TABLE IF EXISTS `dw_editor`;
CREATE TABLE `dw_editor` (
  `editor_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `api` text,
  PRIMARY KEY (`editor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_fee
-- ----------------------------
DROP TABLE IF EXISTS `dw_fee`;
CREATE TABLE `dw_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `nid` int(11) DEFAULT NULL COMMENT '栏目ID',
  `value` varchar(30) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_fields
-- ----------------------------
DROP TABLE IF EXISTS `dw_fields`;
CREATE TABLE `dw_fields` (
  `fields_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `input` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `default` varchar(255) DEFAULT NULL,
  `select` varchar(100) DEFAULT NULL,
  `order` int(11) DEFAULT '0',
  PRIMARY KEY (`fields_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_flag
-- ----------------------------
DROP TABLE IF EXISTS `dw_flag`;
CREATE TABLE `dw_flag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_friends
-- ----------------------------
DROP TABLE IF EXISTS `dw_friends`;
CREATE TABLE `dw_friends` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户',
  `friends_userid` int(11) DEFAULT '0' COMMENT '朋友',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `type` int(2) DEFAULT '0' COMMENT '类型',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='好友';




-- ----------------------------
-- Table structure for dw_friends_request
-- ----------------------------
DROP TABLE IF EXISTS `dw_friends_request`;
CREATE TABLE `dw_friends_request` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户',
  `friends_userid` int(11) DEFAULT '0' COMMENT '朋友',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `type` int(2) DEFAULT '0',
  `content` varchar(250) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='好友记录';



-- ----------------------------
-- Table structure for dw_help
-- ----------------------------
DROP TABLE IF EXISTS `dw_help`;
CREATE TABLE `dw_help` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` int(11) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `smallname` varchar(200) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `litpic` varchar(255) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  `publish` varchar(50) DEFAULT NULL,
  `is_jump` int(1) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `jumpurl` varchar(255) DEFAULT NULL,
  `content` text,
  `order` int(11) DEFAULT '0',
  `hits` int(11) DEFAULT '0',
  `comment` int(11) DEFAULT '0',
  `is_comment` int(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_help_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_help_type`;
CREATE TABLE `dw_help_type` (
  `type_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `pid` int(2) DEFAULT '0',
  `order` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `content` text,
  `list_name` varchar(200) DEFAULT NULL,
  `content_name` varchar(200) DEFAULT NULL,
  `index_tpl` varchar(250) DEFAULT NULL,
  `list_tpl` varchar(250) DEFAULT NULL,
  `content_tpl` varchar(250) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL,
  `keywords` varchar(250) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_home
-- ----------------------------
DROP TABLE IF EXISTS `dw_home`;
CREATE TABLE `dw_home` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `flag` varchar(50) DEFAULT NULL COMMENT '属性',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `publish` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `xiaoqu` varchar(50) DEFAULT NULL COMMENT '小区',
  `shi` varchar(10) DEFAULT NULL COMMENT '室',
  `ting` varchar(10) DEFAULT NULL COMMENT '厅',
  `wei` varchar(10) DEFAULT NULL COMMENT '卫',
  `louceng` varchar(10) DEFAULT NULL COMMENT '楼层',
  `zonglouceng` varchar(10) DEFAULT NULL COMMENT '总楼层',
  `loupan` varchar(10) DEFAULT NULL COMMENT '楼盘',
  `zhucegongsi` varchar(10) DEFAULT NULL COMMENT '是否注册公司',
  `mianji` varchar(10) DEFAULT NULL COMMENT '面积',
  `mianji1` varchar(10) DEFAULT NULL COMMENT '期望面积1',
  `mianji2` varchar(10) DEFAULT NULL COMMENT '期望面积2',
  `fangshi` varchar(10) DEFAULT NULL COMMENT '出租方式',
  `leixing` varchar(10) DEFAULT NULL COMMENT '类型',
  `zhuangxiu` varchar(10) DEFAULT NULL COMMENT '装修',
  `chaoxiang` varchar(10) DEFAULT NULL COMMENT '朝向',
  `zujin` varchar(10) DEFAULT NULL COMMENT '租金',
  `jiage` varchar(10) DEFAULT NULL COMMENT '售价',
  `jiage1` varchar(10) DEFAULT NULL COMMENT '期望售价1',
  `jiage2` varchar(10) DEFAULT NULL COMMENT '期望售价2',
  `jiageleixing` varchar(10) DEFAULT NULL COMMENT '价格类型',
  `lishijingying` varchar(10) DEFAULT NULL COMMENT '历史经营',
  `jibenqingkuang` varchar(10) DEFAULT NULL COMMENT '基本情况',
  `diduan` varchar(10) DEFAULT NULL COMMENT '地段',
  `diduan1` varchar(10) DEFAULT NULL COMMENT '地段1',
  `diduan2` varchar(10) DEFAULT NULL COMMENT '地段2',
  `fukuan` varchar(10) DEFAULT NULL COMMENT '付款方式',
  `linjin` varchar(10) DEFAULT NULL COMMENT '临近',
  `peizhi` varchar(50) DEFAULT NULL COMMENT '配置',
  `tupian` varchar(250) DEFAULT NULL COMMENT '图片',
  `xianxingbie` varchar(250) DEFAULT NULL COMMENT '限制性别',
  `chuzufangjian` varchar(250) DEFAULT NULL COMMENT '出租房间',
  `chuzuleixing` varchar(250) DEFAULT NULL COMMENT '出租类型',
  `content` varchar(255) DEFAULT NULL COMMENT '补充说明',
  `lianxiren` varchar(50) DEFAULT NULL COMMENT '联系人',
  `dianhua` varchar(50) DEFAULT NULL COMMENT '电话',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `updatetime` varchar(50) DEFAULT NULL,
  `updateip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_hongbao
-- ----------------------------
DROP TABLE IF EXISTS `dw_hongbao`;
CREATE TABLE `dw_hongbao` (
  `id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `hongbao_money` decimal(22,0) DEFAULT NULL,
  `remark` varchar(1500) DEFAULT NULL,
  `addtime` varchar(150) DEFAULT NULL,
  `type` varchar(150) DEFAULT NULL,
  `addip` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_huikuan
-- ----------------------------
DROP TABLE IF EXISTS `dw_huikuan`;
CREATE TABLE `dw_huikuan` (
  `id` int(10) DEFAULT NULL,
  `huikuan_money` varchar(150) DEFAULT NULL,
  `huikuan_award` varchar(150) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `remark` varchar(150) DEFAULT NULL,
  `addtime` varchar(150) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `cash_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_integral
-- ----------------------------
DROP TABLE IF EXISTS `dw_integral`;
CREATE TABLE `dw_integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '物品名称',
  `need` double DEFAULT NULL COMMENT '所需积分',
  `number` int(11) DEFAULT NULL COMMENT '数量',
  `ex_number` int(11) DEFAULT '0' COMMENT '已兑换数量',
  `province` int(11) DEFAULT NULL COMMENT '可兑换省份',
  `city` int(11) DEFAULT NULL COMMENT '可兑换城市',
  `area` int(11) DEFAULT NULL COMMENT '区',
  `litpic` varchar(100) DEFAULT NULL COMMENT '图片',
  `content` text COMMENT '详细信息',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `top` int(11) DEFAULT '0' COMMENT '顶次数',
  `flag` varchar(30) DEFAULT NULL COMMENT '定义属性',
  `order` varchar(10) DEFAULT NULL COMMENT '排序',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `addtime` varchar(30) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_integral_convert
-- ----------------------------
DROP TABLE IF EXISTS `dw_integral_convert`;
CREATE TABLE `dw_integral_convert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `integral_id` int(11) DEFAULT NULL COMMENT '兑换物品ID',
  `user_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `number` int(11) DEFAULT NULL COMMENT '数量',
  `need` int(11) DEFAULT NULL COMMENT '所需要的积分',
  `integral` int(11) DEFAULT NULL COMMENT '总积分',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `addtime` varchar(30) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_invest
-- ----------------------------
DROP TABLE IF EXISTS `dw_invest`;
CREATE TABLE `dw_invest` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `flag` varchar(50) DEFAULT NULL COMMENT '属性',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `publish` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `customer` int(11) DEFAULT NULL COMMENT '客服',
  `verify_userid` int(11) DEFAULT NULL COMMENT '审核人',
  `verify_time` varchar(50) DEFAULT NULL COMMENT '审核时间',
  `use` varchar(50) DEFAULT NULL COMMENT '用途',
  `time_limit` varchar(50) DEFAULT NULL COMMENT '借款期限',
  `style` varchar(50) DEFAULT NULL COMMENT '还款方式',
  `account` varchar(50) DEFAULT NULL COMMENT '借贷总金额',
  `apr` varchar(50) DEFAULT NULL COMMENT '年利率',
  `lowest_account` varchar(50) DEFAULT NULL COMMENT '最低投标金额',
  `most_account` varchar(50) DEFAULT NULL COMMENT '最多投标总额',
  `valid_time` varchar(50) DEFAULT NULL COMMENT '有效时间',
  `award` varchar(50) DEFAULT NULL COMMENT '投标奖励',
  `part_account` varchar(50) DEFAULT NULL COMMENT '分摊奖励金额',
  `funds` varchar(50) DEFAULT NULL COMMENT '比例奖励的比例',
  `is_false` varchar(50) DEFAULT NULL COMMENT '标的失败时也同样奖励 ',
  `open_account` varchar(50) DEFAULT NULL COMMENT '公开我的帐户资金情况',
  `open_borrow` varchar(50) DEFAULT NULL COMMENT '公开我的借款资金情况',
  `open_tender` varchar(50) DEFAULT NULL COMMENT '公开我的投标资金情况',
  `open_credit` varchar(50) DEFAULT NULL COMMENT '公开我的信用额度情况',
  `content` text COMMENT '详细说明',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_invite
-- ----------------------------
DROP TABLE IF EXISTS `dw_invite`;
CREATE TABLE `dw_invite` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` smallint(5) unsigned DEFAULT '0',
  `status` smallint(2) unsigned DEFAULT '0',
  `order` smallint(6) DEFAULT '0',
  `flag` char(30) DEFAULT '0',
  `type_id` smallint(5) unsigned DEFAULT '0',
  `name` char(250) DEFAULT NULL,
  `province` char(10) DEFAULT NULL,
  `city` char(10) DEFAULT NULL,
  `area` char(10) DEFAULT NULL,
  `num` char(50) DEFAULT NULL,
  `description` text,
  `demand` text,
  `hits` int(10) DEFAULT '0',
  `addtime` int(10) DEFAULT '0',
  `addip` char(20) DEFAULT NULL,
  `uptime` int(10) DEFAULT '0',
  `upip` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_invite_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_invite_type`;
CREATE TABLE `dw_invite_type` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `typename` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_limitapp
-- ----------------------------
DROP TABLE IF EXISTS `dw_limitapp`;
CREATE TABLE `dw_limitapp` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `account` varchar(255) DEFAULT NULL COMMENT '要增加的额度',
  `recommend_userid` varchar(255) DEFAULT NULL COMMENT '推荐人',
  `content` text COMMENT '详细说明',
  `other_content` text COMMENT '其他地方详细说明',
  `verify_userid` int(11) DEFAULT NULL COMMENT '审核人',
  `verify_time` varchar(50) DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_linkage
-- ----------------------------
DROP TABLE IF EXISTS `dw_linkage`;
CREATE TABLE `dw_linkage` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `status` smallint(2) unsigned DEFAULT '0' COMMENT '状态',
  `order` smallint(6) DEFAULT '0' COMMENT '排序',
  `type_id` smallint(5) unsigned DEFAULT '0' COMMENT '类型',
  `pid` smallint(30) DEFAULT NULL COMMENT '所属联动',
  `name` varchar(250) DEFAULT NULL COMMENT '联动名称',
  `value` varchar(250) DEFAULT NULL COMMENT '联动的值',
  `addtime` varchar(10) DEFAULT '0',
  `addip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`) USING BTREE,
  KEY `type_ida` (`type_id`,`value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1719 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_linkage_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_linkage_type`;
CREATE TABLE `dw_linkage_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order` smallint(6) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `addtime` int(10) DEFAULT '0',
  `addip` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nid` (`nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_links
-- ----------------------------
DROP TABLE IF EXISTS `dw_links`;
CREATE TABLE `dw_links` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `status` smallint(2) unsigned NOT NULL DEFAULT '0',
  `order` smallint(6) NOT NULL DEFAULT '0',
  `flag` smallint(6) DEFAULT NULL,
  `type_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `url` char(60) NOT NULL DEFAULT '',
  `webname` char(30) NOT NULL DEFAULT '',
  `summary` char(200) NOT NULL DEFAULT '',
  `linkman` char(50) NOT NULL DEFAULT '',
  `email` char(50) NOT NULL DEFAULT '',
  `logo` char(100) NOT NULL DEFAULT '',
  `logoimg` char(100) NOT NULL DEFAULT '',
  `province` char(10) NOT NULL DEFAULT '',
  `city` char(10) NOT NULL DEFAULT '',
  `area` char(10) NOT NULL DEFAULT '',
  `hits` int(10) NOT NULL DEFAULT '0',
  `addtime` int(10) NOT NULL DEFAULT '0',
  `addip` char(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_links_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_links_type`;
CREATE TABLE `dw_links_type` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `typename` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dw_liuyan
-- ----------------------------
DROP TABLE IF EXISTS `dw_liuyan`;
CREATE TABLE `dw_liuyan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `fax` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `litpic` varchar(255) DEFAULT NULL,
  `content` text,
  `user_id` int(11) DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `reply` text,
  `reply_id` int(11) DEFAULT '0',
  `replytime` varchar(50) DEFAULT NULL,
  `replyip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_liuyan_set
-- ----------------------------
DROP TABLE IF EXISTS `dw_liuyan_set`;
CREATE TABLE `dw_liuyan_set` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `nid` varchar(100) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_lottery_rule
-- ----------------------------
DROP TABLE IF EXISTS `dw_lottery_rule`;
CREATE TABLE `dw_lottery_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '规则名',
  `start_date` varchar(20) DEFAULT NULL COMMENT '抽奖有效时间起',
  `end_date` varchar(20) DEFAULT NULL COMMENT '抽奖有效时间止',
  `is_accordingtoaward` int(11) DEFAULT NULL COMMENT '是否按奖品抽奖0-否，1是',
  `is_countpoint` int(11) DEFAULT NULL COMMENT '是否按积分抽奖0-否，1是',
  `point_limit` int(11) DEFAULT NULL COMMENT '金额限制',
  `user_limit` int(11) DEFAULT NULL COMMENT '抽奖用户限制：0不限制，1限制用户总次数，2限制当天总次数',
  `description` varchar(3000) DEFAULT NULL COMMENT '规则描述',
  `gmt_create` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `gmt_modfied` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `msg_type` int(4) DEFAULT NULL COMMENT '0不需要发送中奖提醒信息；1站内信 ，1：短信；2：邮件；3：站内信',
  `context` varchar(8000) DEFAULT NULL COMMENT '发送中奖提醒信息内容模块',
  `subject` varchar(1000) DEFAULT NULL COMMENT '发送中奖提醒信息内容模板标题',
  `single_point_limit` int(11) DEFAULT NULL COMMENT '单次获得点数限制',
  `max_point` int(11) unsigned zerofill DEFAULT NULL COMMENT '总积分上限',
  `max_award_num` int(11) DEFAULT NULL COMMENT '限制用户是的最多中奖次数',
  `max_day_times` int(11) DEFAULT NULL,
  `max_times` int(11) DEFAULT NULL,
  `player_type` int(4) DEFAULT NULL,
  `attribute` varchar(5000) DEFAULT NULL,
  `single_money_limit` int(11) DEFAULT NULL,
  `single_max_point_limit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖表';


-- ----------------------------
-- Table structure for dw_message
-- ----------------------------
DROP TABLE IF EXISTS `dw_message`;
CREATE TABLE `dw_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sent_user` int(11) DEFAULT '0' COMMENT '发送用户',
  `receive_user` int(11) DEFAULT '0' COMMENT '接收用户',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `type` varchar(50) DEFAULT '0' COMMENT '类型',
  `sented` varchar(2) DEFAULT NULL,
  `deltype` int(2) DEFAULT '0',
  `content` text COMMENT '内容',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `is_Authenticate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21597 DEFAULT CHARSET=utf8 COMMENT='短消息';


-- ----------------------------
-- Table structure for dw_module
-- ----------------------------
DROP TABLE IF EXISTS `dw_module`;
CREATE TABLE `dw_module` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `default_field` varchar(200) DEFAULT NULL,
  `content` text,
  `version` varchar(10) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  `fields` int(2) DEFAULT NULL,
  `purview` text,
  `remark` text,
  `issent` int(2) DEFAULT NULL,
  `title_name` varchar(100) DEFAULT NULL,
  `onlyone` int(2) DEFAULT NULL,
  `index_tpl` varchar(50) DEFAULT NULL,
  `list_tpl` varchar(50) DEFAULT NULL,
  `content_tpl` varchar(50) DEFAULT NULL,
  `search_tpl` varchar(100) DEFAULT NULL,
  `article_status` int(2) DEFAULT NULL,
  `visit_type` int(2) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_notice
-- ----------------------------
DROP TABLE IF EXISTS `dw_notice`;
CREATE TABLE `dw_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(66) DEFAULT NULL,
  `sent_user` int(11) DEFAULT NULL,
  `recevie_user` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `content` text,
  `result` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5930 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_notice_config
-- ----------------------------
DROP TABLE IF EXISTS `dw_notice_config`;
CREATE TABLE `dw_notice_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(300) DEFAULT NULL,
  `sms` int(11) DEFAULT NULL,
  `email` int(11) DEFAULT NULL,
  `letters` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_obj_award
-- ----------------------------
DROP TABLE IF EXISTS `dw_obj_award`;
CREATE TABLE `dw_obj_award` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) DEFAULT NULL COMMENT '奖品名',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则ID',
  `level` int(2) DEFAULT NULL COMMENT '奖品级别',
  `rate` int(20) DEFAULT NULL COMMENT '奖品中奖率',
  `point_limit` int(10) DEFAULT NULL COMMENT '抽奖点数限制',
  `bestow` int(11) DEFAULT NULL COMMENT '领用数量',
  `total` int(11) DEFAULT NULL COMMENT '奖品总量',
  `award_limit` tinyint(4) DEFAULT NULL COMMENT '奖品限制 0:奖品总量没有限制 1：奖品总量限制',
  `description` varchar(200) DEFAULT NULL COMMENT '奖品描述',
  `ratio` double(20,10) DEFAULT NULL COMMENT '倍率',
  `obj_value` int(11) DEFAULT NULL COMMENT '奖品属性值(如面额)',
  `pic_url` varchar(200) DEFAULT NULL COMMENT '图片',
  `object_rule` varchar(200) DEFAULT NULL COMMENT '奖品规则描述 ',
  `addtime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `addip` varchar(50) DEFAULT NULL COMMENT '创建IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖品规则表';



-- ----------------------------
-- Table structure for dw_online
-- ----------------------------
DROP TABLE IF EXISTS `dw_online`;
CREATE TABLE `dw_online` (
  `user_id` int(10) unsigned DEFAULT '0',
  `username` varchar(45) DEFAULT NULL,
  `type_id` varchar(10) DEFAULT NULL,
  `tid` int(10) unsigned DEFAULT '0',
  `fid` int(10) unsigned DEFAULT '0',
  `atpage` varchar(30) DEFAULT NULL,
  `ip` varchar(16) DEFAULT NULL,
  `activetime` int(10) unsigned DEFAULT '0',
  KEY `userid` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_online_bank
-- ----------------------------
DROP TABLE IF EXISTS `dw_online_bank`;
CREATE TABLE `dw_online_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `bank_logo` varchar(200) DEFAULT NULL COMMENT '银行logo',
  `bank_value` varchar(50) DEFAULT NULL COMMENT '银行值',
  `payment_interface_id` int(11) DEFAULT NULL COMMENT '第三方支付接口 id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线上银行信息表';



-- ----------------------------
-- Table structure for dw_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `dw_operation_log`;
CREATE TABLE `dw_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `verify_user` int(11) DEFAULT NULL,
  `type` varchar(150) DEFAULT NULL,
  `addtime` varchar(150) DEFAULT NULL,
  `addip` varchar(150) DEFAULT NULL,
  `operationResult` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1390 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_password_token
-- ----------------------------
DROP TABLE IF EXISTS `dw_password_token`;
CREATE TABLE `dw_password_token` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '密保主键ID',
  `user_id` int(10) NOT NULL COMMENT '用户ID',
  `question` varchar(45) NOT NULL COMMENT '密保问题',
  `anwser` varchar(45) NOT NULL COMMENT '密保答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户密码信息表';



-- ----------------------------
-- Table structure for dw_payment
-- ----------------------------
DROP TABLE IF EXISTS `dw_payment`;
CREATE TABLE `dw_payment` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `nid` varchar(100) DEFAULT NULL,
  `status` smallint(3) unsigned DEFAULT '0',
  `style` int(2) DEFAULT NULL,
  `config` longtext,
  `fee_type` int(2) DEFAULT NULL,
  `max_money` int(10) DEFAULT NULL,
  `max_fee` int(10) DEFAULT NULL,
  `description` longtext,
  `order` smallint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_payment_interface
-- ----------------------------
DROP TABLE IF EXISTS `dw_payment_interface`;
CREATE TABLE `dw_payment_interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` varchar(50) DEFAULT NULL COMMENT '商户号',
  `key` varchar(1000) DEFAULT NULL COMMENT '商户key',
  `recharge_fee` varchar(20) DEFAULT NULL COMMENT '充值费率',
  `return_url` varchar(100) DEFAULT NULL COMMENT '回调地址',
  `notice_url` varchar(100) DEFAULT NULL COMMENT '通知地址',
  `is_enable` int(6) DEFAULT NULL COMMENT '是否启用',
  `chartset` varchar(11) DEFAULT NULL COMMENT '编码格式',
  `interface_Into_account` varchar(100) DEFAULT NULL COMMENT '接口转入账户',
  `name` varchar(50) DEFAULT NULL COMMENT '接口名称',
  `interface_value` varchar(50) DEFAULT NULL COMMENT '接口值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方接口信息表';




-- ----------------------------
-- Table structure for dw_payment_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_payment_type`;
CREATE TABLE `dw_payment_type` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `nid` varchar(100) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `description` longtext,
  `order` smallint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_purview
-- ----------------------------
DROP TABLE IF EXISTS `dw_purview`;
CREATE TABLE `dw_purview` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `purview` varchar(50) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `pid` int(5) DEFAULT NULL,
  `level` int(1) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_remind
-- ----------------------------
DROP TABLE IF EXISTS `dw_remind`;
CREATE TABLE `dw_remind` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `status` smallint(2) unsigned DEFAULT '0' COMMENT '状态',
  `order` smallint(6) DEFAULT '0' COMMENT '排序',
  `type_id` smallint(5) unsigned DEFAULT '0' COMMENT '类型',
  `message` smallint(2) unsigned DEFAULT '0' COMMENT '短消息',
  `email` smallint(2) unsigned DEFAULT '0' COMMENT '邮箱',
  `phone` smallint(2) unsigned DEFAULT '0' COMMENT '手机',
  `addtime` int(10) DEFAULT '0',
  `addip` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_remind_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_remind_type`;
CREATE TABLE `dw_remind_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order` smallint(6) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `addtime` int(10) DEFAULT '0',
  `addip` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_remind_user
-- ----------------------------
DROP TABLE IF EXISTS `dw_remind_user`;
CREATE TABLE `dw_remind_user` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` smallint(11) unsigned DEFAULT NULL,
  `remind_id` smallint(5) unsigned DEFAULT NULL,
  `message` smallint(2) unsigned DEFAULT '0' COMMENT '短消息',
  `email` smallint(2) unsigned DEFAULT '0' COMMENT '邮箱',
  `phone` smallint(2) unsigned DEFAULT '0' COMMENT '手机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_reward_statistics
-- ----------------------------
DROP TABLE IF EXISTS `dw_reward_statistics`;
CREATE TABLE `dw_reward_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` tinyint(2) DEFAULT NULL COMMENT '信息类型:1充值奖励，2邀请人奖励，3被邀请人奖励，4投标奖励，5回款续投奖励',
  `reward_user_id` int(11) NOT NULL COMMENT '收到奖励的会员ID',
  `passive_user_id` int(11) NOT NULL COMMENT '发放奖励的会员ID',
  `receive_time` varchar(50) DEFAULT NULL COMMENT '应收奖励时间',
  `receive_yestime` varchar(50) DEFAULT NULL COMMENT '实收奖励时间',
  `receive_account` decimal(20,6) DEFAULT '0.000000' COMMENT '应收金额',
  `receive_yesaccount` decimal(20,6) DEFAULT '0.000000' COMMENT '实收金额',
  `receive_status` tinyint(2) DEFAULT NULL COMMENT '奖励发放状态:1未返现，2已返现，3返现审核不通过，4返现失败，5无用数据',
  `addtime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `endtime` varchar(50) DEFAULT NULL COMMENT '结束时间',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则主键ID',
  `back_type` tinyint(2) DEFAULT NULL COMMENT '返现方式:1定时返现，2自动返现，3人工返现',
  `type_fk_id` int(11) DEFAULT NULL COMMENT '根据type的不同，存放不同的主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖励统计表';



-- ----------------------------
-- Table structure for dw_rule
-- ----------------------------
DROP TABLE IF EXISTS `dw_rule`;
CREATE TABLE `dw_rule` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '规则名',
  `status` tinyint(2) unsigned NOT NULL COMMENT '规则状态：1启用，2停用',
  `addtime` varchar(50) NOT NULL COMMENT '创建时间',
  `nid` varchar(50) NOT NULL COMMENT '规则类型名',
  `remark` varchar(255) NOT NULL COMMENT '规则备注',
  `rule_check` varchar(500) NOT NULL COMMENT '规则约束,存JSON',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_rule_1` (`nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='规则表';



-- ----------------------------
-- Table structure for dw_rule_award
-- ----------------------------
DROP TABLE IF EXISTS `dw_rule_award`;
CREATE TABLE `dw_rule_award` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) DEFAULT NULL COMMENT '规则名',
  `start_date` varchar(10) DEFAULT NULL COMMENT '抽奖有效时间起',
  `end_date` varchar(10) DEFAULT NULL COMMENT '抽奖有效时间止',
  `award_type` tinyint(2) DEFAULT NULL COMMENT '抽奖类型:1按积分抽奖,2按次数抽奖,3按倍率抽奖',
  `time_limit` tinyint(2) DEFAULT NULL COMMENT '抽奖次数限制:0不限制,1限制用户总次数,2限制当天总次数',
  `max_times` int(11) DEFAULT NULL COMMENT '最多抽奖次数',
  `base_point` int(11) DEFAULT NULL COMMENT '基准积分',
  `money_limit` int(11) DEFAULT NULL COMMENT '金额限制',
  `total_money` int(11) DEFAULT NULL COMMENT '总金额',
  `bestow_money` int(11) DEFAULT NULL COMMENT '领用金额',
  `is_absolute` tinyint(2) DEFAULT NULL COMMENT '是否100%中奖  0:否,1:是',
  `msg_type` tinyint(4) DEFAULT NULL COMMENT '中奖提醒 0:不需要,1:站内信,2:短信,3:邮件',
  `context` varchar(4000) DEFAULT NULL COMMENT '发送中奖提醒信息内容模板',
  `subject` varchar(256) DEFAULT NULL COMMENT '发送中奖提醒信息内容模板标题',
  `content` text COMMENT '规则描叙',
  `addtime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `addip` varchar(50) DEFAULT NULL COMMENT '创建IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖规则表';



-- ----------------------------
-- Table structure for dw_rule_key
-- ----------------------------
DROP TABLE IF EXISTS `dw_rule_key`;
CREATE TABLE `dw_rule_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `key` varchar(50) DEFAULT NULL COMMENT 'key值',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` varchar(50) DEFAULT NULL COMMENT 'key的类型',
  `value` varchar(500) DEFAULT NULL COMMENT 'value值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_rule_key_1` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='json表';




-- ----------------------------
-- Table structure for dw_scrollpic
-- ----------------------------
DROP TABLE IF EXISTS `dw_scrollpic`;
CREATE TABLE `dw_scrollpic` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` smallint(5) unsigned DEFAULT '0',
  `status` smallint(2) unsigned DEFAULT '0',
  `sort` smallint(6) DEFAULT '0',
  `flag` smallint(6) DEFAULT NULL,
  `type_id` smallint(5) unsigned DEFAULT '0',
  `url` char(60) DEFAULT '',
  `name` char(100) DEFAULT '',
  `pic` char(200) DEFAULT '',
  `summary` char(250) DEFAULT '',
  `hits` int(10) DEFAULT '0',
  `addtime` int(10) DEFAULT '0',
  `addip` char(20) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_scrollpic_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_scrollpic_type`;
CREATE TABLE `dw_scrollpic_type` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `typename` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_site
-- ----------------------------
DROP TABLE IF EXISTS `dw_site`;
CREATE TABLE `dw_site` (
  `site_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `pid` int(2) DEFAULT '0',
  `rank` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `aurl` varchar(255) DEFAULT NULL,
  `isurl` varchar(2) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `style` varchar(2) DEFAULT NULL,
  `litpic` varchar(50) DEFAULT NULL,
  `content` text,
  `list_name` varchar(200) DEFAULT NULL,
  `content_name` varchar(200) DEFAULT NULL,
  `sitedir` varchar(200) DEFAULT NULL,
  `visit_type` varchar(200) DEFAULT NULL,
  `index_tpl` varchar(250) DEFAULT NULL,
  `list_tpl` varchar(250) DEFAULT NULL,
  `content_tpl` varchar(250) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL,
  `keywords` varchar(250) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `user_id` varchar(11) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_sms
-- ----------------------------
DROP TABLE IF EXISTS `dw_sms`;
CREATE TABLE `dw_sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  `userid` int(11) NOT NULL,
  `time` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_system
-- ----------------------------
DROP TABLE IF EXISTS `dw_system`;
CREATE TABLE `dw_system` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `nid` varchar(50) DEFAULT NULL,
  `value` varchar(250) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `style` int(2) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_upfiles
-- ----------------------------
DROP TABLE IF EXISTS `dw_upfiles`;
CREATE TABLE `dw_upfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '模块',
  `aid` varchar(50) DEFAULT NULL COMMENT '所属模块id',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `user_id` int(11) NOT NULL,
  `filetype` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `filename` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `filesize` int(10) DEFAULT '0' COMMENT '文件大小',
  `fileurl` varchar(200) DEFAULT NULL COMMENT '文件地址',
  `if_cover` int(2) DEFAULT '0' COMMENT '是否封面',
  `order` int(10) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `addtime` varchar(30) DEFAULT NULL COMMENT '添加时间',
  `addip` varchar(30) DEFAULT NULL COMMENT '添加ip',
  `updatetime` varchar(30) DEFAULT NULL COMMENT '修改时间',
  `updateip` varchar(30) DEFAULT NULL COMMENT '修改ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_user
-- ----------------------------
DROP TABLE IF EXISTS `dw_user`;
CREATE TABLE `dw_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT '0',
  `purview` varchar(100) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `paypassword` varchar(50) DEFAULT NULL,
  `islock` int(2) NOT NULL DEFAULT '0',
  `invite_userid` varchar(11) DEFAULT NULL COMMENT '邀请好友',
  `invite_money` varchar(10) DEFAULT '0' COMMENT '邀请注册提成',
  `real_status` varchar(2) DEFAULT NULL,
  `card_type` varchar(10) DEFAULT NULL,
  `card_id` varchar(50) DEFAULT NULL,
  `card_pic1` varchar(150) DEFAULT NULL,
  `card_pic2` varchar(150) DEFAULT NULL,
  `nation` varchar(10) DEFAULT NULL,
  `realname` varchar(20) DEFAULT '',
  `integral` varchar(10) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `avatar_status` int(2) DEFAULT '0',
  `email_status` varchar(50) DEFAULT NULL,
  `phone_status` varchar(50) DEFAULT '0',
  `video_status` int(2) DEFAULT '0' COMMENT '视频认证',
  `scene_status` int(2) DEFAULT '0' COMMENT '现场认证',
  `email` varchar(30) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `litpic` varchar(250) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `qq` varchar(50) DEFAULT NULL,
  `wangwang` varchar(100) DEFAULT NULL,
  `question` varchar(10) DEFAULT NULL,
  `answer` varchar(100) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `remind` text COMMENT '提醒设置',
  `privacy` text COMMENT '隐私设置',
  `logintime` int(11) DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `uptime` varchar(50) DEFAULT NULL,
  `upip` varchar(50) DEFAULT NULL,
  `lasttime` varchar(50) DEFAULT NULL,
  `lastip` varchar(20) DEFAULT NULL,
  `is_phone` int(11) DEFAULT NULL,
  `memberLevel` int(11) DEFAULT '0',
  `serial_id` varchar(10) DEFAULT NULL,
  `serial_status` varchar(100) DEFAULT NULL,
  `hongbao` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=318 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_user_amount
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_amount`;
CREATE TABLE `dw_user_amount` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '调整恢复额度',
  `user_id` int(11) NOT NULL,
  `credit` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `credit_use` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `credit_nouse` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `borrow_vouch` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `borrow_vouch_use` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `borrow_vouch_nouse` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `tender_vouch` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `tender_vouch_use` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `tender_vouch_nouse` decimal(20,6) NOT NULL DEFAULT '0.000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1960 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_user_amountapply
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_amountapply`;
CREATE TABLE `dw_user_amountapply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `account` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `account_new` decimal(20,6) DEFAULT '0.000000',
  `account_old` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `status` int(11) DEFAULT '0',
  `addtime` varchar(30) NOT NULL,
  `content` text NOT NULL,
  `remark` text NOT NULL,
  `verify_remark` varchar(255) DEFAULT NULL,
  `verify_time` varchar(10) DEFAULT NULL,
  `verify_user` int(11) DEFAULT NULL,
  `addip` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_user_amountlog
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_amountlog`;
CREATE TABLE `dw_user_amountlog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `amount_type` varchar(50) NOT NULL,
  `account` decimal(20,6) NOT NULL,
  `account_all` decimal(20,6) NOT NULL,
  `account_use` decimal(20,6) NOT NULL,
  `account_nouse` decimal(20,6) NOT NULL,
  `remark` text NOT NULL,
  `addtime` varchar(50) NOT NULL,
  `addip` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_user_award
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_award`;
CREATE TABLE `dw_user_award` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(20) DEFAULT NULL COMMENT '抽奖用户ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '抽奖用户',
  `level` int(11) DEFAULT NULL COMMENT '奖品级别',
  `award_id` int(11) DEFAULT NULL COMMENT '奖品id',
  `point_reduce` int(11) DEFAULT NULL COMMENT '抽奖消耗点数',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则ID',
  `award_name` varchar(32) DEFAULT NULL COMMENT '奖品名',
  `status` int(11) DEFAULT NULL COMMENT '是否中奖:0不中，1中',
  `addtime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `addip` varchar(50) DEFAULT NULL COMMENT '创建IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖用户表';




-- ----------------------------
-- Table structure for dw_user_backup
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_backup`;
CREATE TABLE `dw_user_backup` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `user_tel` varchar(50) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_phone` varchar(50) NOT NULL,
  `user_qq` varchar(50) NOT NULL,
  `user_address` varchar(50) NOT NULL,
  `user_nation` varchar(50) NOT NULL,
  `user_realname` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `publish` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `marry` varchar(50) DEFAULT NULL COMMENT '婚姻',
  `child` varchar(10) DEFAULT NULL COMMENT '子女',
  `education` varchar(10) DEFAULT NULL COMMENT '学历',
  `income` varchar(10) DEFAULT NULL COMMENT '收入',
  `shebao` varchar(10) DEFAULT NULL COMMENT '社保',
  `shebaoid` varchar(50) DEFAULT NULL COMMENT '社保号',
  `housing` varchar(10) DEFAULT NULL COMMENT '住房条件',
  `car` varchar(10) DEFAULT NULL COMMENT '车',
  `late` varchar(10) DEFAULT NULL COMMENT '逾期',
  `house_address` varchar(10) DEFAULT NULL COMMENT '房产地址',
  `house_area` varchar(10) DEFAULT NULL COMMENT '房产面积',
  `house_year` varchar(10) DEFAULT NULL COMMENT '建筑年份',
  `house_status` varchar(10) DEFAULT NULL COMMENT '供款',
  `house_holder1` varchar(10) DEFAULT NULL COMMENT '房子所有权1',
  `house_holder2` varchar(10) DEFAULT NULL COMMENT '房子所有权1',
  `house_right1` varchar(10) DEFAULT NULL COMMENT '房子份额1',
  `house_right2` varchar(10) DEFAULT NULL COMMENT '房子份额2',
  `house_loanyear` varchar(10) DEFAULT NULL COMMENT '贷款年限',
  `house_loanprice` varchar(10) DEFAULT NULL COMMENT '每月供款',
  `house_balance` varchar(10) DEFAULT NULL COMMENT '贷款余额',
  `house_bank` varchar(10) DEFAULT NULL COMMENT '银行',
  `company_name` varchar(10) DEFAULT NULL COMMENT '公司名称',
  `company_type` varchar(10) DEFAULT NULL COMMENT '公司性质',
  `company_industry` varchar(10) DEFAULT NULL COMMENT '公司行业',
  `company_office` varchar(10) DEFAULT NULL COMMENT '工作职位',
  `company_jibie` varchar(10) DEFAULT NULL COMMENT '工作级别',
  `company_worktime1` varchar(10) DEFAULT NULL COMMENT '工作时间1',
  `company_worktime2` varchar(10) DEFAULT NULL COMMENT '工作时间2',
  `company_workyear` varchar(10) DEFAULT NULL COMMENT '工作年限',
  `company_tel` varchar(50) DEFAULT NULL COMMENT '公司电话',
  `company_address` varchar(100) DEFAULT NULL COMMENT '公司地址',
  `company_weburl` varchar(100) DEFAULT NULL COMMENT '公司网站',
  `company_reamrk` varchar(250) DEFAULT NULL COMMENT '公司备注',
  `private_type` varchar(10) DEFAULT NULL COMMENT '类别',
  `private_date` varchar(10) DEFAULT NULL COMMENT '日期',
  `private_place` varchar(10) DEFAULT NULL COMMENT '场所',
  `private_rent` varchar(10) DEFAULT NULL COMMENT '租金',
  `private_term` varchar(10) DEFAULT NULL COMMENT '租期',
  `private_taxid` varchar(30) DEFAULT NULL COMMENT '工商税务',
  `private_commerceid` varchar(50) DEFAULT NULL COMMENT '工商登记号',
  `private_income` varchar(100) DEFAULT NULL COMMENT '收入',
  `private_employee` varchar(100) DEFAULT NULL COMMENT '雇员',
  `finance_repayment` varchar(100) DEFAULT NULL COMMENT '每月无抵押贷款还款额',
  `finance_property` varchar(100) DEFAULT NULL COMMENT '自有房产',
  `finance_amount` varchar(100) DEFAULT NULL COMMENT '每月房屋按揭金额',
  `finance_car` varchar(10) DEFAULT NULL COMMENT '自有汽车',
  `finance_caramount` varchar(100) DEFAULT NULL COMMENT '每月汽车按揭金额',
  `finance_creditcard` varchar(100) DEFAULT NULL COMMENT '信用卡金额',
  `mate_name` varchar(100) DEFAULT NULL COMMENT '配偶名字',
  `mate_salary` varchar(10) DEFAULT NULL COMMENT '薪资',
  `mate_phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `mate_tel` varchar(100) DEFAULT NULL COMMENT '电话',
  `mate_type` varchar(10) DEFAULT NULL COMMENT '工作类型',
  `mate_office` varchar(10) DEFAULT NULL COMMENT '工作职位',
  `mate_address` varchar(250) DEFAULT NULL COMMENT '地址',
  `mate_income` varchar(100) DEFAULT NULL COMMENT '收入',
  `education_record` varchar(100) DEFAULT NULL COMMENT '学历',
  `education_school` varchar(200) DEFAULT NULL COMMENT '学校',
  `education_study` varchar(200) DEFAULT NULL COMMENT '专业',
  `education_time1` varchar(20) DEFAULT NULL COMMENT '时间1',
  `education_time2` varchar(20) DEFAULT NULL COMMENT '时间2',
  `tel` varchar(50) DEFAULT NULL COMMENT '电话',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `post` varchar(50) DEFAULT NULL COMMENT '邮编',
  `address` varchar(50) DEFAULT NULL COMMENT '邮编',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `linkman1` varchar(50) DEFAULT NULL COMMENT '联系人1',
  `relation1` varchar(50) DEFAULT NULL COMMENT '关系1',
  `tel1` varchar(50) DEFAULT NULL COMMENT '电话1',
  `phone1` varchar(50) DEFAULT NULL COMMENT '手机1',
  `linkman2` varchar(50) DEFAULT NULL COMMENT '联系人2',
  `relation2` varchar(50) DEFAULT NULL COMMENT '关系2',
  `tel2` varchar(50) DEFAULT NULL COMMENT '电话2',
  `phone2` varchar(50) DEFAULT NULL COMMENT '手机2',
  `linkman3` varchar(50) DEFAULT NULL COMMENT '联系人3',
  `relation3` varchar(50) DEFAULT NULL COMMENT '关系3',
  `tel3` varchar(50) DEFAULT NULL COMMENT '电话3',
  `phone3` varchar(50) DEFAULT NULL COMMENT '手机3',
  `msn` varchar(50) DEFAULT NULL COMMENT 'MSN',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ',
  `wangwang` varchar(50) DEFAULT NULL COMMENT 'WANGWANG',
  `ability` varchar(250) DEFAULT NULL COMMENT '个人能力',
  `interest` varchar(250) DEFAULT NULL COMMENT '个人爱好',
  `others` varchar(250) DEFAULT NULL COMMENT '其他说明',
  `experience` text COMMENT '工作经历',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `updatetime` varchar(50) DEFAULT NULL,
  `updateip` varchar(50) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_id_2` (`user_id`) USING BTREE,
  KEY `user_id_3` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_cache`;
CREATE TABLE `dw_user_cache` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `kefu_userid` int(11) DEFAULT NULL,
  `kefu_username` varchar(50) DEFAULT NULL,
  `kefu_addtime` int(11) DEFAULT NULL,
  `vip_status` int(2) DEFAULT '0',
  `vip_remark` varchar(250) DEFAULT NULL,
  `vip_money` varchar(100) DEFAULT NULL,
  `vip_verify_remark` varchar(100) DEFAULT NULL,
  `vip_verify_time` varchar(100) DEFAULT NULL,
  `bbs_topics_num` int(11) DEFAULT '0',
  `bbs_posts_num` int(11) DEFAULT '0',
  `credit` int(11) DEFAULT '0' COMMENT '积分',
  `account` int(11) DEFAULT '0' COMMENT '帐户总额',
  `account_use` int(11) DEFAULT '0' COMMENT '可用金额',
  `account_nouse` int(11) DEFAULT '0' COMMENT '冻结金额',
  `account_waitin` int(11) DEFAULT '0' COMMENT '代收总额',
  `account_waitintrest` int(11) DEFAULT '0' COMMENT '代收利息',
  `account_intrest` int(11) DEFAULT '0' COMMENT '净赚利息',
  `account_award` int(11) DEFAULT '0' COMMENT '投标奖励',
  `account_payment` int(11) DEFAULT '0' COMMENT '待还总额',
  `account_expired` int(11) DEFAULT '0' COMMENT '逾期总额',
  `account_waitvip` int(11) DEFAULT '0' COMMENT 'vip会费',
  `borrow_amount` int(11) DEFAULT '3000' COMMENT '借款额度',
  `vouch_amount` int(11) NOT NULL DEFAULT '0' COMMENT '担保额度',
  `borrow_loan` int(11) DEFAULT '0' COMMENT '成功借出',
  `borrow_success` int(11) DEFAULT '0' COMMENT '成功借款',
  `borrow_wait` int(11) DEFAULT '0' COMMENT '等待满标',
  `borrow_paymeng` int(11) DEFAULT '0' COMMENT '待还借款',
  `friends_apply` int(11) DEFAULT '0',
  `login_fail_times` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_user_copy
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_copy`;
CREATE TABLE `dw_user_copy` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT '0',
  `purview` varchar(100) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `paypassword` varchar(50) DEFAULT NULL,
  `islock` int(2) NOT NULL DEFAULT '0',
  `invite_userid` varchar(11) DEFAULT NULL COMMENT '邀请好友',
  `invite_money` varchar(10) DEFAULT '0' COMMENT '邀请注册提成',
  `real_status` varchar(2) DEFAULT NULL,
  `card_type` varchar(10) DEFAULT NULL,
  `card_id` varchar(50) DEFAULT NULL,
  `card_pic1` varchar(150) DEFAULT NULL,
  `card_pic2` varchar(150) DEFAULT NULL,
  `nation` varchar(10) DEFAULT NULL,
  `realname` varchar(20) DEFAULT '',
  `integral` varchar(10) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `avatar_status` int(2) DEFAULT '0',
  `email_status` varchar(50) DEFAULT NULL,
  `phone_status` varchar(50) DEFAULT '0',
  `video_status` int(2) DEFAULT '0' COMMENT '视频认证',
  `scene_status` int(2) DEFAULT '0' COMMENT '现场认证',
  `email` varchar(30) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `litpic` varchar(250) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `qq` varchar(50) DEFAULT NULL,
  `wangwang` varchar(100) DEFAULT NULL,
  `question` varchar(10) DEFAULT NULL,
  `answer` varchar(100) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `remind` text COMMENT '提醒设置',
  `privacy` text COMMENT '隐私设置',
  `logintime` int(11) DEFAULT '0',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `uptime` varchar(50) DEFAULT NULL,
  `upip` varchar(50) DEFAULT NULL,
  `lasttime` varchar(50) DEFAULT NULL,
  `lastip` varchar(20) DEFAULT NULL,
  `is_phone` int(11) DEFAULT NULL,
  `memberLevel` int(11) DEFAULT '0',
  `serial_id` varchar(10) DEFAULT NULL,
  `serial_status` varchar(100) DEFAULT NULL,
  `hongbao` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1957 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dw_user_log
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_log`;
CREATE TABLE `dw_user_log` (
  `log_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `query` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `result` varchar(100) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_user_sendemail_log
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_sendemail_log`;
CREATE TABLE `dw_user_sendemail_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `msg` text,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12907 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_user_trend
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_trend`;
CREATE TABLE `dw_user_trend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `content` text,
  `addtime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作记录';



-- ----------------------------
-- Table structure for dw_user_type
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_type`;
CREATE TABLE `dw_user_type` (
  `type_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `purview` text,
  `order` varchar(10) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  `summary` varchar(200) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_user_typechange
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_typechange`;
CREATE TABLE `dw_user_typechange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `status` int(2) NOT NULL,
  `old_type` varchar(10) NOT NULL,
  `new_type` varchar(10) NOT NULL,
  `content` varchar(255) NOT NULL,
  `addtime` varchar(20) NOT NULL,
  `addip` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_user_typepurview
-- ----------------------------
DROP TABLE IF EXISTS `dw_user_typepurview`;
CREATE TABLE `dw_user_typepurview` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_type_id` int(10) DEFAULT NULL,
  `purview_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3399 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dw_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `dw_userinfo`;
CREATE TABLE `dw_userinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT '0' COMMENT '所属站点',
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `flag` varchar(50) DEFAULT NULL COMMENT '属性',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `publish` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `marry` varchar(50) DEFAULT NULL COMMENT '婚姻',
  `child` varchar(250) DEFAULT NULL COMMENT '子女',
  `education` varchar(250) DEFAULT NULL COMMENT '学历',
  `income` varchar(250) DEFAULT NULL COMMENT '收入',
  `shebao` varchar(250) DEFAULT NULL COMMENT '社保',
  `shebaoid` varchar(50) DEFAULT NULL COMMENT '社保号',
  `housing` varchar(250) DEFAULT NULL COMMENT '住房条件',
  `car` varchar(250) DEFAULT NULL COMMENT '车',
  `late` varchar(250) DEFAULT NULL COMMENT '逾期',
  `house_address` varchar(250) DEFAULT NULL COMMENT '房产地址',
  `house_area` varchar(250) DEFAULT NULL COMMENT '房产面积',
  `house_year` varchar(250) DEFAULT NULL COMMENT '建筑年份',
  `house_status` varchar(250) DEFAULT NULL COMMENT '供款',
  `house_holder1` varchar(250) DEFAULT NULL COMMENT '房子所有权1',
  `house_holder2` varchar(250) DEFAULT NULL COMMENT '房子所有权1',
  `house_right1` varchar(250) DEFAULT NULL COMMENT '房子份额1',
  `house_right2` varchar(250) DEFAULT NULL COMMENT '房子份额2',
  `house_loanyear` varchar(250) DEFAULT NULL COMMENT '贷款年限',
  `house_loanprice` varchar(250) DEFAULT NULL COMMENT '每月供款',
  `house_balance` varchar(250) DEFAULT NULL COMMENT '贷款余额',
  `house_bank` varchar(250) DEFAULT NULL COMMENT '银行',
  `company_name` varchar(250) DEFAULT NULL COMMENT '公司名称',
  `company_type` varchar(250) DEFAULT NULL COMMENT '公司性质',
  `company_industry` varchar(250) DEFAULT NULL COMMENT '公司行业',
  `company_office` varchar(250) DEFAULT NULL COMMENT '工作职位',
  `company_jibie` varchar(250) DEFAULT NULL COMMENT '工作级别',
  `company_worktime1` varchar(250) DEFAULT NULL COMMENT '工作时间1',
  `company_worktime2` varchar(250) DEFAULT NULL COMMENT '工作时间2',
  `company_workyear` varchar(250) DEFAULT NULL COMMENT '工作年限',
  `company_tel` varchar(250) DEFAULT NULL COMMENT '公司电话',
  `company_address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `company_weburl` varchar(200) DEFAULT NULL COMMENT '公司网站',
  `company_reamrk` varchar(250) DEFAULT NULL COMMENT '公司备注',
  `private_type` varchar(200) DEFAULT NULL COMMENT '类别',
  `private_date` varchar(200) DEFAULT NULL COMMENT '日期',
  `private_place` varchar(200) DEFAULT NULL COMMENT '场所',
  `private_rent` varchar(200) DEFAULT NULL COMMENT '租金',
  `private_term` varchar(200) DEFAULT NULL COMMENT '租期',
  `private_taxid` varchar(250) DEFAULT NULL COMMENT '工商税务',
  `private_commerceid` varchar(250) DEFAULT NULL COMMENT '工商登记号',
  `private_income` varchar(250) DEFAULT NULL COMMENT '收入',
  `private_employee` varchar(250) DEFAULT NULL COMMENT '雇员',
  `finance_repayment` varchar(250) DEFAULT NULL COMMENT '每月无抵押贷款还款额',
  `finance_property` varchar(250) DEFAULT NULL COMMENT '自有房产',
  `finance_amount` varchar(250) DEFAULT NULL COMMENT '每月房屋按揭金额',
  `finance_car` varchar(250) DEFAULT NULL COMMENT '自有汽车',
  `finance_caramount` varchar(200) DEFAULT NULL COMMENT '每月汽车按揭金额',
  `finance_creditcard` varchar(200) DEFAULT NULL COMMENT '信用卡金额',
  `mate_name` varchar(200) DEFAULT NULL COMMENT '配偶名字',
  `mate_salary` varchar(200) DEFAULT NULL COMMENT '薪资',
  `mate_phone` varchar(200) DEFAULT NULL COMMENT '手机',
  `mate_tel` varchar(200) DEFAULT NULL COMMENT '电话',
  `mate_type` varchar(250) DEFAULT NULL COMMENT '工作类型',
  `mate_office` varchar(250) DEFAULT NULL COMMENT '工作职位',
  `mate_address` varchar(250) DEFAULT NULL COMMENT '地址',
  `mate_income` varchar(200) DEFAULT NULL COMMENT '收入',
  `education_record` varchar(200) DEFAULT NULL COMMENT '学历',
  `education_school` varchar(200) DEFAULT NULL COMMENT '学校',
  `education_study` varchar(200) DEFAULT NULL COMMENT '专业',
  `education_time1` varchar(250) DEFAULT NULL COMMENT '时间1',
  `education_time2` varchar(250) DEFAULT NULL COMMENT '时间2',
  `tel` varchar(250) DEFAULT NULL COMMENT '电话',
  `phone` varchar(250) DEFAULT NULL COMMENT '手机',
  `post` varchar(250) DEFAULT NULL COMMENT '邮编',
  `address` varchar(250) DEFAULT NULL COMMENT '邮编',
  `province` varchar(250) DEFAULT NULL COMMENT '省份',
  `city` varchar(250) DEFAULT NULL COMMENT '城市',
  `area` varchar(250) DEFAULT NULL COMMENT '区',
  `linkman1` varchar(250) DEFAULT NULL COMMENT '联系人1',
  `relation1` varchar(250) DEFAULT NULL COMMENT '关系1',
  `tel1` varchar(250) DEFAULT NULL COMMENT '电话1',
  `phone1` varchar(250) DEFAULT NULL COMMENT '手机1',
  `linkman2` varchar(250) DEFAULT NULL COMMENT '联系人2',
  `relation2` varchar(250) DEFAULT NULL COMMENT '关系2',
  `tel2` varchar(250) DEFAULT NULL COMMENT '电话2',
  `phone2` varchar(250) DEFAULT NULL COMMENT '手机2',
  `linkman3` varchar(250) DEFAULT NULL COMMENT '联系人3',
  `relation3` varchar(250) DEFAULT NULL COMMENT '关系3',
  `tel3` varchar(250) DEFAULT NULL COMMENT '电话3',
  `phone3` varchar(250) DEFAULT NULL COMMENT '手机3',
  `msn` varchar(250) DEFAULT NULL COMMENT 'MSN',
  `qq` varchar(250) DEFAULT NULL COMMENT 'QQ',
  `wangwang` varchar(250) DEFAULT NULL COMMENT 'WANGWANG',
  `ability` varchar(250) DEFAULT NULL COMMENT '个人能力',
  `interest` varchar(250) DEFAULT NULL COMMENT '个人爱好',
  `others` varchar(250) DEFAULT NULL COMMENT '其他说明',
  `experience` text COMMENT '工作经历',
  `addtime` varchar(50) DEFAULT NULL,
  `addip` varchar(50) DEFAULT NULL,
  `updatetime` varchar(50) DEFAULT NULL,
  `updateip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for dw_usertrack
-- ----------------------------
DROP TABLE IF EXISTS `dw_usertrack`;
CREATE TABLE `dw_usertrack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `login_time` varchar(13) DEFAULT NULL,
  `login_ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33210 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for dw_winning_information
-- ----------------------------
DROP TABLE IF EXISTS `dw_winning_information`;
CREATE TABLE `dw_winning_information` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) DEFAULT NULL COMMENT '抽奖用户id',
  `username` varchar(32) DEFAULT NULL COMMENT '抽奖用户名',
  `level` int(11) DEFAULT NULL COMMENT '奖品级别',
  `award_id` int(11) DEFAULT NULL COMMENT '奖品id',
  `point_reduce` int(11) DEFAULT NULL COMMENT '抽奖消耗点数',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则id',
  `award_name` varchar(32) DEFAULT NULL COMMENT '奖品名',
  `status` int(11) DEFAULT NULL COMMENT '是否中奖 0不中，1中',
  `gmt_create` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `attributes` varchar(2000) DEFAULT NULL,
  `winning_money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖记录表';



-- ----------------------------
-- Table structure for view_auto_invest
-- ----------------------------
DROP TABLE IF EXISTS `view_auto_invest`;
CREATE TABLE `view_auto_invest` (
  `user_id` int(11) DEFAULT NULL,
  `apr_first` bigint(12) DEFAULT NULL,
  `last_login_time` double DEFAULT NULL,
  `last_tender_time` double DEFAULT NULL,
  `tender_account` double DEFAULT NULL,
  `use_money` double DEFAULT NULL,
  `pr` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for view_auto_invest_set
-- ----------------------------
DROP TABLE IF EXISTS `view_auto_invest_set`;
CREATE TABLE `view_auto_invest_set` (
  `addtime` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- View structure for temp_borrow
-- ----------------------------
DROP VIEW IF EXISTS `temp_borrow`;
CREATE ALGORITHM=UNDEFINED DEFINER=`sfdai135`@`%` SQL SECURITY DEFINER VIEW `temp_borrow` AS select sum(`r`.`repayment_account`) AS `repayTotal`,sum(`r`.`interest`) AS `repayInterest`,min(`r`.`repayment_time`) AS `repayTime`,`b`.`id` AS `borrow_id`,`b`.`user_id` AS `user_id` from (`dw_borrow` `b` left join `dw_borrow_repayment` `r` on((`r`.`borrow_id` = `b`.`id`))) where ((`b`.`status` in (3,6,7)) and (`r`.`status` <> 1)) group by `b`.`user_id`,`r`.`borrow_id`;








































