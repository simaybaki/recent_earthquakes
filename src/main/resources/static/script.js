document.getElementById('earthquakeForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const country = document.getElementById('country').value;
    const days = document.getElementById('days').value;

    fetch(`/earthquakes?country=${country}&days=${days}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('No Earthquakes were recorded past ' + days + ' days.');
            }
            return response.json();
        })
        .then(data => {
            let resultOl = document.getElementById('results');
            resultOl.innerHTML = '';

            data.forEach(earthquake => {
                let li = document.createElement('li');
                li.innerHTML = `
                    <strong>Country: ${country}</strong>
                    <p>Place: ${earthquake.place}</p>
                    <p>Magnitude: ${earthquake.magnitude}</p>
                    <p>Date: ${earthquake.date}</p>
                    <p>Time: ${earthquake.time}</p>
                `;
                resultOl.appendChild(li);
            });
        })
        .catch(error => {
            let resultOl = document.getElementById('results');
            resultOl.innerHTML = `<p style="color: red;">${error.message}</p>`;
        });
});
