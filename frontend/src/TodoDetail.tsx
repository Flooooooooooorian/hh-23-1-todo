import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {Todo} from "./Todo";
import {toast} from "react-toastify";
import {Typography} from "@mui/material";


export default function TodoDetail() {

    const [todo, setTodo] = useState<Todo>()

    // useParams hook von react-router, nutzen wir um Pfadvariabel aus der url auszulesen
    const {id} = useParams<{ id: string }>()

    useEffect(() => {
        if (id) {
            loadTodoById(id)
        }
    }, [])

    function loadTodoById(id: string) {
        axios.get('/api/todo/' + id)
            .then((response) => {
                setTodo(response.data)
            })
            .catch((error) => {
                toast.error("Todo not found")
            })
    }

    return (
        <div>
            {
                todo
                    ? <div>
                        <Typography>{todo.id}</Typography>
                        <Typography>{todo.description}</Typography>
                        <Typography>{todo.status}</Typography>
                    </div>
                    : <div>Loading...</div>
            }

        </div>
    )
}
