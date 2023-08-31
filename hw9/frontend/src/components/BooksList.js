import {Grid} from "antd";
import {Content} from "antd/es/layout/layout";
import {useEffect, useState} from "react";
import {fetchBooks} from "../api/booksApi";

function BooksList() {
    const [books, setBooks] = useState([]);

    console.log(books)

    useEffect(() => {
        fetchBooks().then((data) => {
            setBooks(data)
        })
    }, [])

    return (
        <div>
            flkjsdoifshdifjdka
        </div>
    )
}

export default BooksList