import { Navigate, useLocation } from 'react-router-dom';

const AuthGuard = ({ children }) => {
  const location = useLocation();
  
  // TODO: 实现实际的身份验证逻辑
  const isAuthenticated = localStorage.getItem('token');

  if (!isAuthenticated) {
    // 将用户重定向到登录页面，同时保存他们试图访问的URL
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
};

export default AuthGuard;