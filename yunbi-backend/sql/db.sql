-- 自动更新update_time的触发器函数
CREATE OR REPLACE FUNCTION update_table_update_time()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 用户表
CREATE TABLE IF NOT EXISTS users
(
    id          INT8 PRIMARY KEY,
    username    VARCHAR(16)  NOT NULL UNIQUE,
    phone       VARCHAR(64)  NOT NULL UNIQUE,
    email       VARCHAR(64)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status      INT2         NOT NULL DEFAULT 1
);

-- 用户表字段注释
COMMENT ON TABLE users IS '系统用户表';
COMMENT ON COLUMN users.id IS '用户ID（雪花ID）';
COMMENT ON COLUMN users.username IS '用户名（唯一）';
COMMENT ON COLUMN users.phone IS '手机号（唯一）';
COMMENT ON COLUMN users.email IS '电子邮箱（唯一）';
COMMENT ON COLUMN users.password IS '加密后的密码';
COMMENT ON COLUMN users.create_time IS '记录创建时间';
COMMENT ON COLUMN users.update_time IS '记录最后更新时间';
COMMENT ON COLUMN users.status IS '用户状态：0-禁用, 1-启用';

CREATE TRIGGER update_users_update_time
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_table_update_time();

-- 图表分析表
CREATE TABLE IF NOT EXISTS chart_analysis
(
    id                  INT8 PRIMARY KEY,
    goal                TEXT      NOT NULL,
    data                TEXT      NOT NULL,
    type                INT2,
    generate_chart      JSON,
    generate_conclusion TEXT,
    create_time         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time         TIMESTAMP NULL,
    user_id             int8 REFERENCES users (id) ON DELETE CASCADE
);

-- 图表分析表字段注释
COMMENT ON TABLE chart_analysis IS '智能BI图表分析表';
COMMENT ON COLUMN chart_analysis.id IS '图表ID（雪花ID）';
COMMENT ON COLUMN chart_analysis.goal IS '图表分析目标描述';
COMMENT ON COLUMN chart_analysis.data IS '原始数据（JSON格式）';
COMMENT ON COLUMN chart_analysis.type IS '图表类型：1-折线图, 2-柱状图, 3-饼图等';
COMMENT ON COLUMN chart_analysis.generate_chart IS '生成的图表配置';
COMMENT ON COLUMN chart_analysis.generate_conclusion IS '生成的分析结论';
COMMENT ON COLUMN chart_analysis.create_time IS '记录创建时间';
COMMENT ON COLUMN chart_analysis.update_time IS '记录最后更新时间';
COMMENT ON COLUMN chart_analysis.delete_time IS '记录逻辑删除时间';
COMMENT ON COLUMN chart_analysis.user_id IS '关联用户ID';

CREATE TRIGGER update_users_update_time
    BEFORE UPDATE
    ON chart_analysis
    FOR EACH ROW
EXECUTE FUNCTION update_table_update_time();