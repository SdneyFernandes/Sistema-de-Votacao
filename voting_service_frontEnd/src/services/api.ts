import axios from 'axios'

// Configuração para o serviço de USUÁRIOS (voting-system-user-service)
const userApi = axios.create({
  baseURL: 'http://localhost:8083/api/users'
})

// Configuração para o serviço de VOTOS (voting-system-vote-service)
const voteApi = axios.create({
  baseURL: 'http://localhost:8082/api'
})

// Definição de tipos
interface UserUpdateData {
  username?: string
  email?: string
  password?: string
}

interface RegisterData {
  username: string
  email: string
  password: string
}

interface LoginData {
  email: string
  password: string
}

interface VoteSessionData {
  title: string
  options: string[]
  startAt: string
  endAt: string
}

// EXPORTAÇÕES - USUÁRIOS
export const getAllUsers = () => userApi.get('/')
export const getUserById = (id: number) => userApi.get(`/${id}`)
export const updateUser = (id: number, data: UserUpdateData) =>
  userApi.put(`/${id}`, data)
export const deleteUser = (id: number) => userApi.delete(`/${id}`)
export const registerUser = (data: RegisterData) =>
  userApi.post('/register', data)
export const loginUser = (data: LoginData) => userApi.post('/login', data)
export const getCurrentUser = (token: string) =>
  userApi.get('/me', {
    headers: { Authorization: `Bearer ${token}` }
  })

// EXPORTAÇÕES - SESSÕES DE VOTAÇÃO
export const createVoteSession = (data: VoteSessionData) =>
  voteApi.post('/votes_session/create', data)
export const getAllVoteSessions = () => voteApi.get('/votes_session/')
export const getVoteSession = (id: number) =>
  voteApi.get(`/votes_session/${id}`)
export const deleteVoteSession = (id: number) =>
  voteApi.delete(`/votes_session/${id}`)
export const getVoteResults = (id: number) =>
  voteApi.get(`/votes_session/${id}/results`)

// EXPORTAÇÕES - VOTOS
export const castVote = (
  voteSessionId: number,
  userId: number,
  option: string
) =>
  voteApi.post(`/votes/${voteSessionId}/cast?userId=${userId}&option=${option}`)

export { userApi, voteApi }
