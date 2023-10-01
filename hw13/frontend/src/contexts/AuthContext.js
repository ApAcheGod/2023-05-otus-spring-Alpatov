import React, {createContext, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";

export const authContext = createContext({auth: false, setAuth: () => undefined});

const AuthContext = ({children}) => {
    const navigate = useNavigate();
    const params = useParams();

    const [auth, setAuth] = useState(false);

    useEffect(() => {
        if (!auth) {
            navigate('/login')
        }
    }, [params])

    return <authContext.Provider value={{auth, setAuth}}>
        { children }
    </authContext.Provider>
}

export default AuthContext