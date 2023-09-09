export const deleteBookById = (id) => {
    return fetch(`/api/book/${id}`, {
        method: 'DELETE'
    });
}

export const getBooks = () => {
    return fetch('/api/book').then(data => data.json());
}

export const getBookById = (id) => {
    return fetch(`/api/book/${id}`).then(data => data.json());
}

export const createBook = (body) => {
    return fetch('/api/book', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
}

export const editBook = (body) => {
    return fetch('/api/book', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
}

export const getCommentsByBookId = (id) => {
    return fetch(`/api/book/${id}/comment`).then(data => data.json());
}

export const postCommentByBookId = (body) => {
    return fetch(`/api/comment`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}

export const editCommentsByBookId = (body) => {
    return fetch(`/api/comment`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}

export const deleteCommentById = (id) => {
    return fetch(`/api/comment/${id}`, {
        method: 'DELETE'
    });
}

export const createComment = (body) => {
    return fetch("/api/comment", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}