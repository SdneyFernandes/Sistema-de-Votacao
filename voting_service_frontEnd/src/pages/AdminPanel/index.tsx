import { useEffect, useState } from 'react'
import { userApi, voteApi } from '../../services/api'
import { useNavigate } from 'react-router-dom'
import { getUserRole } from '../../utils/auth'
import {
  AdminContainer,
  Title,
  SectionTitle,
  List,
  ListItem,
  DeleteButton
} from './styles'

interface User {
  id: number
  username: string
  role: string
}

interface Session {
  id: number
  title: string
}

const AdminPanel = () => {
  const [users, setUsers] = useState<User[]>([])
  const [sessions, setSessions] = useState<Session[]>([])
  const navigate = useNavigate()

  useEffect(() => {
    if (getUserRole() !== 'ADMIN') {
      alert('Acesso negado!')
      navigate('/dashboard')
      return
    }

    const fetchData = async () => {
      try {
        const usersResponse = await userApi.get('/api/users')
        const sessionsResponse = await voteApi.get('/api/votes_session')
        setUsers(usersResponse.data)
        setSessions(sessionsResponse.data)
      } catch (error) {
        alert('Erro ao carregar dados!')
      }
    }
    fetchData()
  }, [navigate])

  const deleteUser = async (id: number) => {
    try {
      await userApi.delete(`/api/users/${id}`)
      setUsers(users.filter((user) => user.id !== id))
    } catch (error) {
      alert('Erro ao deletar usuário!')
    }
  }

  const deleteSession = async (id: number) => {
    try {
      await voteApi.delete(`/api/votes_session/${id}`)
      setSessions(sessions.filter((session) => session.id !== id))
    } catch (error) {
      alert('Erro ao deletar sessão!')
    }
  }

  return (
    <AdminContainer>
      <Title>Painel de Administração</Title>

      <SectionTitle>Usuários</SectionTitle>
      <List>
        {users.map((user) => (
          <ListItem key={user.id}>
            {user.username} - {user.role}
            <DeleteButton onClick={() => deleteUser(user.id)}>
              Deletar
            </DeleteButton>
          </ListItem>
        ))}
      </List>

      <SectionTitle>Votações</SectionTitle>
      <List>
        {sessions.map((session) => (
          <ListItem key={session.id}>
            {session.title}
            <DeleteButton onClick={() => deleteSession(session.id)}>
              Deletar
            </DeleteButton>
          </ListItem>
        ))}
      </List>
    </AdminContainer>
  )
}

export default AdminPanel
