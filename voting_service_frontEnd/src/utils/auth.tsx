export const getToken = () => localStorage.getItem('token')

export const setToken = (token: string) => {
  localStorage.setItem('token', token)
}

export const removeToken = () => {
  localStorage.removeItem('token')
}

export const getUserRole = () => {
  const role = localStorage.getItem('role')
  return role ? role : 'USER'
}

export const getUserId = () => {
  return localStorage.getItem('userId') || null
}
