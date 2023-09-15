import React, {useContext, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {
    createBook, deleteCommentById, editBook, getBookById, getCommentsByBookId, editCommentsByBookId,
    createComment as createCommentAPI
} from "../api/book";
import {getGenres} from "../api/genres";
import {getAuthors} from "../api/authors";
import {Button, Col, Form, Input, Row, Select, Spin, Typography} from "antd";
import Comment from "./Comment";
import {authContext} from "../contexts/AuthContext";

let Book = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const {auth} = useContext(authContext);

    const [initialBook, setInitialBook] = useState(null);
    const [isBookLoading, setIsBookLoading] = useState(false);

    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');

    const [genres, setGenres] = useState([])
    const genresSelect = genres?.map((genre) => ({label: genre.name, value: genre.id})) || []

    const [authors, setAuthors] = useState([])
    const authorsSelect = authors?.map((author) => ({label: `${author.name} ${author.lastName}`, value: author.id})) || []

    const getBook = () => {
        if (!auth) {
            return;
        }
        setIsBookLoading(true);
        getBookById(id).then((data) => {
            setInitialBook({
                ...data,
                authors: data?.authors?.map(author => author.id) || [],
                genres: data?.genres?.map(genre => genre.id) || []
            });
            setIsBookLoading(false);
        })
    }

    const getComments = () => {
        if (!auth) {
            return;
        }
        getCommentsByBookId(id).then((data) => {
            setComments(data);
        })
    }

    useEffect(() => {
        if (!auth) {
            return;
        }
        if (id) {
            getBook()
            getComments()
        }
        getGenres().then((data) => {
            setGenres(data)
        })
        getAuthors().then((data) => {
            setAuthors(data)
        })
    }, [])

    const deleteComment = (id) => {
        deleteCommentById(id).then(getComments);
    }

    const createComment = () => {
        createCommentAPI({
            bookId: id,
            comment: newComment
        }).then(getComments).then(() => setNewComment(''));
    }

    const editComment = (comment) => {
        editCommentsByBookId(comment).then(getComments);
    }

    const onSubmit = (values) => {
        const formValues = {
            ...values,
            id,
            authors: authors.filter((author) => {
                return values.authors?.find(authorId => author.id === authorId)
            }),
            genres: genres.filter((genre) => {
                return values.genres?.find(genreId => genre.id === genreId)
            })
        }
        if (id) {
            editBook(formValues).then(getBook)
        } else {
            createBook(formValues).then(() => navigate("/"))
        }
    }

    if (!auth) {
        return null;
    }

    if (isBookLoading) {
        return <Row style={{marginTop: 100}}>
            <Col>
                <Spin size={'large'}/>
            </Col>
        </Row>
    }

    return (
        <Row gutter={32}>
            <Col span={24}>
                <Typography.Title level={2}>{`${id ? 'Редактирование книги' : 'Новая книга'}`}</Typography.Title>
            </Col>
            <Col span={24}>
                <Form
                    name={'book'}
                    initialValues={initialBook}
                    labelCol={{span: 24}}
                    wrapperCol={{span: 24}}
                    onFinish={onSubmit}
                >
                    <Form.Item
                        name={'title'}
                        label={'Название'}
                        rules={[{required: true, message: 'Обязательное поле'}]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name={'authors'}
                        label={'Авторы'}
                        rules={[{required: true, message: 'Обязательное поле'}]}
                    >
                        <Select defaultValue={initialBook?.authors} options={authorsSelect} mode={'multiple'} />
                    </Form.Item>
                    <Form.Item
                        name={'genres'}
                        label={'Жанры'}
                        rules={[{required: true, message: 'Обязательное поле'}]}
                    >
                        <Select defaultValue={initialBook?.genres} options={genresSelect} mode={'multiple'} />
                    </Form.Item>
                    <Form.Item
                        name={'pageCount'}
                        label={'Число страниц'}
                        rules={[{required: true, message: 'Обязательное поле'}]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name={'publicationYear'}
                        label={'Год публикации'}
                        rules={[{required: true, message: 'Обязательное поле'}]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item>
                        <Button htmlType={'submit'} type={'primary'}>Сохранить</Button>
                    </Form.Item>
                </Form>
            </Col>
            {id && <Col span={24}>
                <Row style={{marginBottom: 40}}>
                    <Col>
                        <Typography.Title level={4}>
                            Комментарии
                        </Typography.Title>
                    </Col>
                </Row>
                <Row style={{marginBottom: 20}} gutter={[24, 24]}>
                    <Col>
                        <input value={newComment} onChange={(e) => setNewComment(e.target.value)}/>
                    </Col>
                    <Col>
                        <Button onClick={createComment}>
                            Создать
                        </Button>
                    </Col>
                </Row>
                <Row gutter={[24, 24]}>
                    {comments.map(comment => <Comment
                            key={comment.id}
                            comment={comment}
                            editComment={editComment}
                            deleteComment={deleteComment}
                        />
                    )}
                </Row>
            </Col>}
        </Row>
    )
}

export default Book;