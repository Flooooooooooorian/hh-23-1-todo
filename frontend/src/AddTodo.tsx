import {FormEvent, useState} from "react";
import {NewTodo} from "./Todo";
import {Button, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";

type AddTodoProps = {
    addTodo: (newTodo: NewTodo) => void
}


export default function AddTodo(props: AddTodoProps) {

    const [description, setDescription] = useState<string>('')

    // useNavigate hook von React-Router ermöglicht uns programmtisch zu navigieren
    const navigate = useNavigate()


    function onSaveTodo(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()

        if (description === undefined || description === '') {
            console.error("Description requierd")
            return
        }

        const newTodo: NewTodo = {description: description, status: 'OPEN'}

        props.addTodo(newTodo)

        // nutzt den useNavigate hook um uns auf andere Seiten zu navigieren
        navigate('/todos')
    }

    return (
        <div>
            <form onSubmit={onSaveTodo}>
                <TextField label='Description'
                           required
                           value={description}
                           onChange={(event) => {
                               setDescription(event.target.value)
                           }}/>
                <Button className='mybutton' variant='contained' color="success" type='submit'>Save</Button>
            </form>
        </div>
    )
}
