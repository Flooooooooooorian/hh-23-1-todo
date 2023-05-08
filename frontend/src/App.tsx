import React from 'react';
import './App.css';
import 'react-toastify/dist/ReactToastify.css';
import Header from "./Header";
import TodoGallery from "./TodoGallery";
import AddTodo from "./AddTodo";
import useTodos from "./useTodos";
import {ToastContainer} from "react-toastify";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import TodoDetail from "./TodoDetail";
import LoginPage from "./LoginPage";
import useUser from "./useUser";
import ProtectedRoutes from "./ProtectedRoutes";

function App() {

    const {token, login} = useUser()
    const {todos, addTodo, deleteTodo, updateTodo} = useTodos(token)

    return (
        //BrowserRouter 1mal pro Projekt, kümmert sich um die ganze navigierung
        <BrowserRouter>
            <div className="App">
                <ToastContainer autoClose={3000}/>
                <Header/>

                {/*routes Gruppierung von Routen, kann es mehrere von geben*/}
                <Routes>
                    <Route path='/login' element={<LoginPage onLogin={login}/>}/>

                    {/*Alle Routen für die man eingeloggt sein muss*/}
                    <Route element={<ProtectedRoutes user={token}/>}>
                        {/*Route eine Unterseite auf unserer Seite*/}
                        <Route element={<Navigate to='/todos'/>}/>

                        {/*    path gibt die url der Seite an*/}
                        {/*element geben wir was auf dieser Seite dargestellt werden soll*/}
                        <Route path='/todos'
                               element={<TodoGallery todos={todos} updateTodo={updateTodo} deleteTodo={deleteTodo}/>}/>
                        <Route path='/todos/add'
                               element={<AddTodo addTodo={addTodo}/>}/>
                        {/*                 :variabel können dynamische Pfadvariabeln angeben */}
                        <Route path='/todos/:id' element={<TodoDetail/>}/>
                    </Route>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
