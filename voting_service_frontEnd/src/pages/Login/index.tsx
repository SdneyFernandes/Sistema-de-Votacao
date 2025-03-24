import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { userApi, getCurrentUser } from '../../services/api'
import {
  AuthContainer,
  AuthForm,
  AuthInput,
  AuthButton,
  SwitchAuth
} from '../../styles/AuthStyles'

const Login = () => {
  const navigate = useNavigate()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await userApi.post('/login', { email, password })
      console.log('Resposta do backend:', response) // Verifique toda a resposta
      console.log('Dados da resposta:', response.data) // Apenas os dados

      const token = response.data
      console.log('Token recebido:', token) // Debug do token

      if (!token) throw new Error('Token não recebido')

      localStorage.setItem('token', token)

      const userResponse = await userApi.get('/me', {
        headers: { Authorization: `Bearer ${token}` }
      })

      console.log('Usuário logado:', userResponse.data)
      const userRole = userResponse.data.role
      localStorage.setItem('role', userRole)

      navigate(userRole === 'ADMIN' ? '/create-vote' : '/dashboard')
    } catch (error) {
      console.error('Erro ao fazer login:', error)
      alert('Erro ao fazer login!')
    }
  }

  return (
    <AuthContainer>
      <AuthForm onSubmit={handleLogin}>
        <h2>Login</h2>
        <AuthInput
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <AuthInput
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <AuthButton type="submit">Entrar</AuthButton>
        <SwitchAuth>
          Não tem uma conta?{' '}
          <a onClick={() => navigate('/register')}>Registre-se</a>
        </SwitchAuth>
      </AuthForm>
    </AuthContainer>
  )
}

export default Login
