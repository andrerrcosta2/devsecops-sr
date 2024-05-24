function getCsrfToken() {
    return document.querySelector('input[name="_csrf"]').value;
}

function markFavorite(movieId) {
    const csrfToken = getCsrfToken();
    fetch(`/movies/favorite/${movieId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('Marked as favorite:', data);
        // You can add some UI feedback here
    })
    .catch(error => console.error('Error marking as favorite:', error));
}

function markWantToWatch(movieId) {
    const csrfToken = getCsrfToken();
    fetch(`/movies/want-to-watch/${movieId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('Marked as want to watch:', data);
        // You can add some UI feedback here
    })
    .catch(error => console.error('Error marking as want to watch:', error));
}

function markAlreadyWatched(movieId) {
    const csrfToken = getCsrfToken();
    fetch(`/movies/already-watched/${movieId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('Marked as already watched:', data);
        // You can add some UI feedback here
    })
    .catch(error => console.error('Error marking as already watched:', error));
}