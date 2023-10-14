export const getAuthors = () => {
    return fetch('/api/author').then(data => data.json());
}