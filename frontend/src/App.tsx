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

function App() {

    const {todos, addTodo, deleteTodo, updateTodo} = useTodos()

    return (
        //BrowserRouter 1mal pro Projekt, kümmert sich um die ganze navigierung
        <BrowserRouter>
            <div className="App">
                <ToastContainer autoClose={3000}/>
                <Header/>

                {/*routes Gruppierung von Routen, kann es mehrere von geben*/}
                <Routes>
                    {/*Route eine Unterseite auf unserer Seite*/}
                    <Route element={<Navigate to='/todos'/>}/>

                    {/*    path gibt die url der Seite an*/}
                    {/*element geben wir was auf dieser Seite dargestellt werden soll*/}
                    <Route path='/todos'
                           element={<TodoGallery todos={todos} updateTodo={updateTodo} deleteTodo={deleteTodo}/>}/>
                    <Route path='/todos/add'
                           element={<AddTodo addTodo={addTodo}/>}/>
                    {/*                 :variabel können dynamische Pfadvariabeln angeben */}
                    <Route path='/todos/:id' element={<TodoDetail />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
