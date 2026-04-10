import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            // Talking to your Java Backend
            const response = await api.post('/auth/login', { username, password });
            login(response.data.token);
            navigate('/dashboard');
        } catch (error) {
            alert("Login failed! Check your Java backend console and CORS settings.");
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-gray-50 p-4">
        <div className="w-full max-w-sm rounded-2xl bg-white p-8 shadow-xl border border-gray-100">
        <h1 className="mb-6 text-center text-3xl font-bold text-gray-900">TaskFlow</h1>
            <form onSubmit={handleSubmit} className="space-y-4">
    <div>
        <label className="block text-sm font-medium text-gray-700">Username</label>
        <input
    type="text"
    className="mt-1 w-full rounded-lg border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-200"
    value={username}
    onChange={(e) => setUsername(e.target.value)}
    />
    </div>
    <div>
    <label className="block text-sm font-medium text-gray-700">Password</label>
        <input
    type="password"
    className="mt-1 w-full rounded-lg border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-200"
    value={password}
    onChange={(e) => setPassword(e.target.value)}
    />
    </div>
    <button className="w-full rounded-lg bg-blue-600 py-2.5 font-semibold text-white transition hover:bg-blue-700">
        Sign In
    </button>
    </form>
    </div>
    </div>
);
};

export default Login;