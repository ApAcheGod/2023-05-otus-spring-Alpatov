import {HashRouter, Route, Routes, useNavigate} from "react-router-dom";
import Books from "./Books";
import React, {useContext, useState} from "react";
import Book from "./Book";
import {Button, Col, Row} from "antd";
import Login from "./login/Login";
import AuthContext, {authContext} from "../contexts/AuthContext";


export const Header = (props) => (
    <h1>{props.title}</h1>
);

const Navbar = () => {
    const navigate = useNavigate();
    const {setAuth} = useContext(authContext);

    const logoutRequest = async () => {
        await fetch("/logout").then(() => setAuth(false))
    }

    return (
        <Row gutter={[24, 24]}>
            <Col>
                <Button onClick={() => navigate('/')}>
                    Все книги
                </Button>
            </Col>
            <Col>
                <Button onClick={() => navigate('/book/new')}>
                    Новая книга
                </Button>
            </Col>
            <Col>
                <Button onClick={logoutRequest}>LOGOUT</Button>
            </Col>
        </Row>
    )
}

function App() {
    const {auth} = useContext(authContext)
    const [genres, setGenres] = useState(null)
    const [authors, setAuthors] = useState(null)

    let performGenres = () => {
        fetch('/api/genre')
            .then(response => response.json())
            .then(genres => setGenres(genres));
    }

    let performAuthors = () => {
        fetch('/api/author')
            .then(response => response.json())
            .then(authors => setAuthors(authors));
    }


    React.useEffect(() => {
        if (!auth) {
            return;
        }
        performGenres()
        performAuthors()
    }, [])

        return (
                <HashRouter>
                    <AuthContext>
                        <Navbar />
                        <Routes>
                            <Route path='/' element={<Books />}/>
                            <Route path='/book' element={<Books />}/>
                            <Route path='/book/:id' element={<Book />} />
                            <Route path='/book/new' element={<Book />} />
                            <Route path={'/login'} element={<Login />}/>
                        </Routes>
                    </AuthContext>
                </HashRouter>
        );
}

export default App;