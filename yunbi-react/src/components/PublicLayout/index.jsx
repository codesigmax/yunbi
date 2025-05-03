import { Layout } from 'antd';
import { Outlet } from 'react-router-dom';

const { Content } = Layout;

const PublicLayout = () => {
  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Content>
        <Outlet />
      </Content>
    </Layout>
  );
};

export default PublicLayout;