import {Typography} from "@mui/material";
import React from "react";
import {Link, NavLink} from "react-router-dom";


export default function Header() {

    return (
        <div>
            <Typography variant='h1'>Todo Board</Typography>

            {/*Komponenten von React Router zum Navigieren*/}
            <Link to='/todos'>Todos</Link>
            <NavLink to='/todos/add'>Add</NavLink>
            <NavLink to='/login'>Login</NavLink>
        </div>
    )
}
