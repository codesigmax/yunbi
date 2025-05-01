import { Card, Row, Col, Typography } from 'antd';
import { LineChartOutlined, BarChartOutlined, PieChartOutlined } from '@ant-design/icons';

const { Title } = Typography;

const Home = () => {
  const chartTypes = [
    {
      title: '折线图分析',
      icon: <LineChartOutlined style={{ fontSize: 48 }} />,
      description: '适用于展示数据随时间变化的趋势',
    },
    {
      title: '柱状图分析',
      icon: <BarChartOutlined style={{ fontSize: 48 }} />,
      description: '适用于比较不同类别之间的数据差异',
    },
    {
      title: '饼图分析',
      icon: <PieChartOutlined style={{ fontSize: 48 }} />,
      description: '适用于展示数据的构成和占比情况',
    },
  ];

  return (
    <div>
      <Title level={2}>智能 BI 平台</Title>
      <Row gutter={[16, 16]} style={{ marginTop: 24 }}>
        {chartTypes.map((chart, index) => (
          <Col xs={24} sm={12} md={8} key={index}>
            <Card hoverable>
              <div style={{ textAlign: 'center' }}>
                {chart.icon}
                <Title level={4} style={{ marginTop: 16 }}>{chart.title}</Title>
                <p>{chart.description}</p>
              </div>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default Home;