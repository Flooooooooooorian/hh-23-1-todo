import {useState} from "react";
import axios from "axios";


export default function useUser() {
    const [token, setToken] = useState<string>()

    function login(username: string, password: string) {
        return axios.post("/api/users/login", {username, password}, )
            .then(response => {
                setToken(response.data)
            })
    }

    return {token, login}
}
