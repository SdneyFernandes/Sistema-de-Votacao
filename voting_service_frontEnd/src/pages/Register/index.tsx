/* eslint-disable prettier/prettier */
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { userApi } from '../../services/api'
import {
  AuthContainer,
  AuthForm,
  AuthInput,
  AuthButton,
  SwitchAuth
} from '../../styles/AuthStyles'

const Register = () => {
  const [userName, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [role, setRole] = useState('USER')
  const navigate = useNavigate()

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await userApi.post('/register', { userName, email, password, role })
      alert('Registro bem-sucedido!')
      navigate('/')
    } catch (error) {
      alert('Erro ao registrar')
      console.error(error)
    }
  }

  return (
    <AuthContainer>
      <AuthForm onSubmit={handleRegister}>
        <h2>Cadastro</h2>
        <AuthInput
          type="text"
          placeholder="Nome"
          value={userName}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
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
        <select value={role} onChange={(e) => setRole(e.target.value)}>
          <option value="USER">Usuário</option>
          <option value="ADMIN">Administrador</option>
        </select>
        <AuthButton type="submit">Registrar</AuthButton>
        <SwitchAuth>
          Já tem uma conta? <a onClick={() => navigate('/')}>Faça login</a>
        </SwitchAuth>
      </AuthForm>
    </AuthContainer>
  )
}

export default Register