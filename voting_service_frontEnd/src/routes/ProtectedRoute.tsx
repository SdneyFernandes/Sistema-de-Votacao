import { Navigate } from 'react-router-dom'

const ProtectedRoute = ({
  children,
  role
}: {
  children: JSX.Element
  role: string
}) => {
  const userRole = localStorage.getItem('role')

  if (!userRole) return <Navigate to="/login" />
  if (userRole !== role) return <h2>Acesso Negado</h2>

  return children
}

export default ProtectedRoute
