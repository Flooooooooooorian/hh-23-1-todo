import './TodoCard.css'
import {Todo} from "./Todo";
import {Button, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";

type Props = {
    todo: Todo,
    updateTodo: (todo: Todo) => void,
    deleteTodo: (id: string) => void
}

export default function TodoCard(props: Props) {

    const navigate = useNavigate()

    const nextStatus: {OPEN: "IN_PROGRESS", IN_PROGRESS: "DONE", DONE: "DONE"} = {
        "OPEN": "IN_PROGRESS",
        "IN_PROGRESS": "DONE",
        "DONE": "DONE",
    }

    function onAdvanceClick() {
        //Wir erstellen eine Kopie des Todos mit den geupdateten Werten
        //                         {} wir erstellen ein neues Object
        //                          ... (spreading) übernehmen wir alle eigenschaften des alten todos
        //                                       , wir fügen disese Eigenschaft neu hinzu oder ersetzten den wer der eigenschaft
        const todoToUpdate: Todo = {...props.todo, status: nextStatus[props.todo.status]}

        props.updateTodo(todoToUpdate)
    }

    function onDeleteClick() {
        props.deleteTodo(props.todo.id)
    }

    return (
        <div className='todo-card'>
            <Typography>{props.todo.description}</Typography>
            <Typography>{props.todo.status}</Typography>
            <Typography>{props.todo.id}</Typography>
            <Button onClick={() => {navigate('/todos/' + props.todo.id)}}>Details</Button>
            {props.todo.status !== 'DONE' && <Button onClick={onAdvanceClick}>Advance</Button>}
            {props.todo.status === 'DONE' && <Button onClick={onDeleteClick}>Delete</Button>}

        </div>
    )
}
