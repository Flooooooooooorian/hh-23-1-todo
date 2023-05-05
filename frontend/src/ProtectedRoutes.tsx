import {Navigate, Outlet} from "react-router-dom";

type Props = {
    user: string | undefined
}

export default function ProtectedRoutes(props: Props) {

    const authenticated = props.user !== undefined

    //                  Zeigt alle Unter Routen
    return (
        authenticated ? <Outlet /> : <Navigate to={"/login"} />
    )
}
