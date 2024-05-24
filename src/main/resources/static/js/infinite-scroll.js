document.addEventListener('DOMContentLoaded', function () {
    let page = 1;
    const movieContainer = document.getElementById('movie-container');
    const loadMoreBtn = document.getElementById('load-more-btn');

    const loadMoreMovies = () => {
        page++;
        fetch(`/movies/${page}`)
            .then(response => response.json())
            .then(movies => {
            movies.forEach(movie => {
                const movieLink = document.createElement('a');
                movieLink.href = "javascript:void(0)";
                movieLink.setAttribute('th:onclick', 'loadMovieDetails2(this)');
                movieLink.setAttribute('th:id', movie.id);
                movieLink.classList.add('home-movie-link');

                const movieBox = document.createElement('div');
                movieBox.classList.add('home-movie-box');
                movieBox.style.backgroundImage = `url(http://image.tmdb.org/t/p/w300/${movie.posterPath})`;

                const movieInfo = document.createElement('div');
                movieInfo.classList.add('home-movie-info');

                const title = document.createElement('h2');
                title.innerText = movie.title;

                const overview = document.createElement('p');
                overview.innerText = movie.overview;

                movieInfo.appendChild(title);
                movieInfo.appendChild(overview);

                movieBox.appendChild(movieInfo);
                movieLink.appendChild(movieBox);

                movieContainer.appendChild(movieLink);
            });
        })
            .catch(error => console.error('Error fetching more movies:', error));
    };

    const handleLoadMoreClick = () => {
        loadMoreMovies();
    };

    loadMoreBtn.addEventListener('click', handleLoadMoreClick);
});
