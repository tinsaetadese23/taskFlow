import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login';

// A simple wrapper to protect pages
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
};

function App() {
  return (
      <AuthProvider>
        <Router>
          <Routes>
            {/* Public Path */}
            <Route path="/login" element={<Login />} />

            {/* Protected Path (We will build Dashboard next) */}
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <div className="p-10 text-2xl font-bold">Welcome to the Dashboard!</div>
              </ProtectedRoute>
            } />

            {/* Default redirect */}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </Router>
      </AuthProvider>
  );
}

export default App;