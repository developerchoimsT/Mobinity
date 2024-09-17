import React, { useContext, createContext, useState, useEffect } from 'react';
import axios from 'axios';
import {BrowserRouter as Router, Route, Routes, Link, useLocation} from 'react-router-dom';
import Login from '../auth/Login';
import Join from '../auth/Join';
import './App.css';
import Main from "./Main";
import Search from "../search/Search";
import './axios/axiosInterceptor.js';
import AdminMain from "../admin/adminMain";

// Context 생성
const UserContext = createContext(null);

// App 컴포넌트
function App() {

    const id = localStorage.getItem("id");
    const role = localStorage.getItem("role");

    useEffect(() => {
        handleLogout();
    }, [id, role]);

    const handleLogout = async () => {
        await axios.delete('http://hoopi.p-e.kr/api/hoopi/logout', { params: { id: id } })
            .then(response => {
                localStorage.removeItem('id');
                localStorage.removeItem('role');
                alert(response.data);
                window.location.href = '/';  // 수정된 부분
            })
            .catch(error => {
                alert(error.response.data);
            });
    }

    return (
            <Router>
                <div className='mainNav-container'>
                    <div className='mainNav-sns-box'>

                    </div>
                    <div className='mainNav-img-box'>
                        <Link to='/'><img src='/nata_logo.png' /></Link>
                    </div>
                    <div className='mainNav-link-box'>
                        {role == null || role === '' || id == null || id === '' ? (
                            <>
                                <Link to="/join">회원가입</Link>
                                <Link to="/login">로그인</Link>
                            </>
                        ) : (
                            <span>
                                {id} 님 환영합니다 <Link to="/" onClick={handleLogout}>로그아웃</Link>
                            </span>
                        )}
                    </div>
                </div>
                <Search/>
                <div>
                    <Routes>
                        <Route path='/' element={<Main/>}/>
                        <Route path='/join' element={<Join />}/>
                        <Route path='/login' element={<Login />}/>
                        <Route path='/admin/main' element={<AdminMain/>}/>
                        <Route path='/admin/board' element={<AdminMain/>}/>
                        <Route path='/admin/user' element={<AdminMain/>}/>
                        <Route path='/admin/order' element={<AdminMain/>}/>
                        <Route path='/admin/product' element={<AdminMain/>}/>
                    </Routes>
                </div>
            </Router>
    );
}

export default App;
export {UserContext};