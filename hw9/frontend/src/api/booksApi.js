import {API_URL} from "../consts/api";

export const fetchBooks = async () => {
    return (await fetch(`${API_URL}/books`, {
        method: 'GET',
    }))
        .json()
};