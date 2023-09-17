import {HashRouter, Route, Routes, useNavigate} from "react-router-dom";
import Books from "./Books";
import React, {useState} from "react";
import Book from "./Book";
import {Button, Col, Row} from "antd";

export const Header = (props) => (
    <h1>{props.title}</h1>
);

const Navbar = () => {
    const navigate = useNavigate();

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
        </Row>
    )
}

function App() {

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
        performGenres()
        performAuthors()
    }, [])

    return (
        <HashRouter>
            <Navbar />

            <Routes>
                <Route path='/' element={<Books />}/>
                <Route path='/book' element={<Books />}/>
                <Route path='/book/:id' element={<Book />} />
                <Route path='/book/new' element={<Book />} />
            </Routes>
        </HashRouter>
    );
}

export default App;