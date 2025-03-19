// Fetch forecast data
async function getForecast(city) {
    const url = `https://api.openweathermap.org/data/2.5/forecast?q=${city}&units=metric&appid=${apiKey}`;
    
    try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.cod === '404') {
            alert('City not found');
            return;
        }

        displayForecast(data);
    } catch (error) {
        console.error('Error fetching forecast data:', error);
    }
}

// Display forecast
function displayForecast(data) {
    const forecastList = document.getElementById('forecastList');
    forecastList.innerHTML = '';  // Clear previous forecast

    data.list.slice(0, 5).forEach(forecast => {
        const date = new Date(forecast.dt * 1000);
        const forecastItem = document.createElement('div');
        forecastItem.classList.add('forecast-item');
        forecastItem.innerHTML = `
            <p><strong>${date.toDateString()}</strong></p>
            <p>Temp: ${forecast.main.temp}°C</p>
            <p>Condition: ${forecast.weather[0].description}</p>
        `;
        forecastList.appendChild(forecastItem);
    });

    document.getElementById('forecast').style.display = 'block';
}

// Modify getWeather function to also get forecast data
async function getWeather() {
    const city = document.getElementById('city').value;
    if (!city) {
        alert('Please enter a city name');
        return;
    }

    const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${apiKey}`;
    
    try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.cod === '404') {
            alert('City not found');
            return;
        }

        displayWeather(data);
        getForecast(city); // Fetch forecast data after getting current weather
    } catch (error) {
        console.error('Error fetching weather data:', error);
    }
}
