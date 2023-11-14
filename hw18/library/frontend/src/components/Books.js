import React, {useContext, useEffect, useState} from 'react'
import {Table, Tag, Typography} from "antd";
import {deleteBookById, getBooks} from "../api/book";
import {authContext} from "../contexts/AuthContext";

const columns = [
    {
        title: 'Заголовок',
        dataIndex: 'title',
        key: 'title',
        render: (_, {id, title}) => {
            return (
                <a href={`/#/book/${id}`}>{title}</a>
            )
        }
    },
    {
        title: 'Авторы',
        dataIndex: 'authors',
        key: 'authors',
        render: (_, {authors}) => {
            return (
                <div>{authors.map((author) => {
                    return (
                        <>
                        <Typography.Text>{author.name} {author.lastName}</Typography.Text>
                        <br />
                        </>
                    )
                })}
                </div>
            )
        }
    },
    {
        title: 'Жанры',
        dataIndex: 'genres',
        key: 'genres',
        render: (_, {genres}) => {
            return (
                <>{genres.map((genre) => {
                    return (
                        <Tag>
                            {genre.name}
                        </Tag>
                    )
                })}
                </>
            )
        }
    },
    {
        title: 'Количество страниц',
        dataIndex: 'pageCount',
        key: 'pageCount',
    },
    {
        title: 'Год публикации',
        dataIndex: 'publicationYear',
        key: 'publicationYear',
    },
    {
        title: '',
        dataIndex: 'delete',
        key: 'delete',
        render: (_, {id}) => {
            const deleteBook = () => {
                deleteBookById(id).then(() => location.reload())
            }
            return (
                <div>
                <a onClick={deleteBook}>Удалить</a>
                </div>
            )
        }
    }
]

const Books = () => {
    const [books, setBooks] = useState(null)
    const {auth} = useContext(authContext)

    useEffect(() => {
        if (!auth) {
            return;
        }
        getBooks().then(books => setBooks(books))
    }, [])

    return (
        <Table
            columns={columns}
            dataSource={books}
        />
    )
};

export default Books;