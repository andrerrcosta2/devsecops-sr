function getCsrfToken() {
    return document.querySelector('input[name="_csrf"]').value;
}

function markFavorite(movieDetails) {
    const csrfToken = getCsrfToken();

    fetch(`/user/activity/favorite/${movieDetails}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(evaluation)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Marked as favorite:', data);
    })
    .catch(error => console.error('Error marking as favorite:', error));
}

function markWantToWatch(movieDetails) {
    const csrfToken = getCsrfToken();

    const csrfToken = getCsrfToken();
    fetch(`/user/activity/want-to-watch/${movieDetails}}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(evaluation)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Marked as want to watch:', data);
    })
    .catch(error => console.error('Error marking as want to watch:', error));
}

function markAlreadyWatched(movieDetails) {
    const csrfToken = getCsrfToken();

    fetch(`/user/activity/already-watched/${movieDetails}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(evaluation)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Marked as already watched:', data);
    })
    .catch(error => console.error('Error marking as already watched:', error));
}