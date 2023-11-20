export const getGenres = () => {
    return fetch('/api/genre').then(data => data.json());
}