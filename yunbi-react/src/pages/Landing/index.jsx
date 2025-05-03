import { Button, Typography, Row, Col, Card, Space, Layout } from 'antd';
import { useNavigate } from 'react-router-dom';
import { LineChartOutlined, BarChartOutlined, PieChartOutlined, RocketOutlined } from '@ant-design/icons';

const { Title, Paragraph } = Typography;

const Landing = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: <LineChartOutlined style={{ fontSize: 48, color: '#1890ff' }} />,
      title: '智能分析',
      description: '只需输入数据和分析需求，即可自动生成可视化图表',
    },
    {
      icon: <BarChartOutlined style={{ fontSize: 48, color: '#52c41a' }} />,
      title: '多样图表',
      description: '支持折线图、柱状图、饼图等多种图表类型',
    },
    {
      icon: <PieChartOutlined style={{ fontSize: 48, color: '#722ed1' }} />,
      title: '图表分析',
      description: '自动生成图表分析结论，辅助决策',
    },
  ];

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Layout.Content style={{ padding: '60px 0', background: 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)' }}>
        <Row justify="center" style={{ marginBottom: 48 }}>
          <Col xs={20} sm={16} md={12} lg={8}>
            <div style={{ textAlign: 'center' }}>
              <Title style={{ fontSize: 48, marginBottom: 16 }}>
                <RocketOutlined style={{ marginRight: 16 }} />
                智能 BI 平台
              </Title>
              <Paragraph style={{ fontSize: 24, marginBottom: 48, color: '#666' }}>
                让数据分析变得简单而智能
              </Paragraph>
              <Space size="large" style={{ marginBottom: 48 }}>
                <Button type="primary" size="large" onClick={() => navigate('/login')} style={{ height: 50, padding: '0 40px', fontSize: 18, borderRadius: 25 }}>
                  立即登录
                </Button>
                <Button size="large" onClick={() => navigate('/register')} style={{ height: 50, padding: '0 40px', fontSize: 18, borderRadius: 25 }}>
                  免费注册
                </Button>
              </Space>
            </div>
          </Col>
        </Row>

        <Row gutter={[24, 24]} justify="center">
          {features.map((feature, index) => (
            <Col xs={20} sm={8} key={index}>
              <Card hoverable style={{ textAlign: 'center', borderRadius: 16, boxShadow: '0 4px 12px rgba(0,0,0,0.1)' }}>
                <div>{feature.icon}</div>
                <Title level={3} style={{ marginTop: 16 }}>{feature.title}</Title>
                <Paragraph>{feature.description}</Paragraph>
              </Card>
            </Col>
          ))}
        </Row>
      </Layout.Content>
    </Layout>
  );
};

export default Landing;