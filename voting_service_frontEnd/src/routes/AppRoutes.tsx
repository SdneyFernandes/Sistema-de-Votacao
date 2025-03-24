import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from '../pages/Login'
import Register from '../pages/Register'
import Dashboard from '../pages/Dashboard'
import Vote from '../pages/Vote'
import ResultsPage from '../pages/ResultsPage'
import CreateVote from '../pages/CreateVote'
import AdminPanel from '../pages/AdminPanel'
import ProtectedRoute from '../routes/ProtectedRoute'

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/vote/:id" element={<Vote />} />
        <Route path="/results/:id" element={<ResultsPage />} />
        <Route
          path="/create-vote"
          element={
            <ProtectedRoute role="ADMIN">
              <CreateVote />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminPanel />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  )
}

export default AppRoutes
