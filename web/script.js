// API endpoint configuration
const API_BASE_URL = '/api';
const ENDPOINTS = {
    CREATE_ALERT: `${API_BASE_URL}/alerts`,
    MARKET_DATA: `${API_BASE_URL}/market-data`
};

// Cache for market data
let currentMarketData = null;

async function fetchCurrentPrice() {
    const ticker = document.getElementById('ticker').value.toUpperCase();
    if (!ticker) {
        showError('ticker', 'Please enter a ticker symbol');
        return;
    }

    try {
        const response = await fetch(`${ENDPOINTS.MARKET_DATA}/${ticker}`);
        if (!response.ok) {
            throw new Error('Failed to fetch market data');
        }

        const data = await response.json();
        currentMarketData = data;
        
        const priceDisplay = document.getElementById('currentPrice');
        priceDisplay.textContent = `${ticker}: $${data.currentPrice.toFixed(2)}`;
        
        // Pre-fill the target price with current price
        document.getElementById('targetPrice').value = data.currentPrice.toFixed(2);
    } catch (error) {
        showError('ticker', 'Failed to fetch current price. Please try again.');
        console.error('Error fetching market data:', error);
    }
}

function validateTicker(ticker) {
    // Basic ticker validation rules
    const tickerRegex = /^[A-Z]{1,5}$/;
    if (!tickerRegex.test(ticker)) {
        showError('ticker', 'Ticker must be 1-5 uppercase letters');
        return false;
    }
    return true;
}

function validateTargetPrice(targetPrice, ticker) {
    if (isNaN(targetPrice) || targetPrice <= 0) {
        showError('targetPrice', 'Target price must be a positive number');
        return false;
    }

    // If we have current market data, add price range validation
    if (currentMarketData && currentMarketData.ticker === ticker) {
        const currentPrice = currentMarketData.currentPrice;
        const maxChange = currentPrice * 0.5; // 50% price change limit
        
        if (Math.abs(targetPrice - currentPrice) > maxChange) {
            showError('targetPrice', `Target price should be within 50% of current price ($${(currentPrice - maxChange).toFixed(2)} - $${(currentPrice + maxChange).toFixed(2)})`);
            return false;
        }
    }
    
    return true;
}

async function handleSubmit(event) {
    event.preventDefault();
    clearErrors();

    // Get form values
    const ticker = document.getElementById('ticker').value.toUpperCase();
    const targetPrice = parseFloat(document.getElementById('targetPrice').value);
    const condition = document.querySelector('input[name="condition"]:checked').value;
    const notificationMethods = Array.from(
        document.querySelectorAll('input[name="notificationMethods"]:checked')
    ).map(input => input.value);

    // Enhanced validation
    if (!validateTicker(ticker) ||
        !validateTargetPrice(targetPrice, ticker) ||
        !validateNotificationMethods(notificationMethods)) {
        return false;
    }

    // Create alert object
    const alert = {
        ticker,
        targetPrice,
        triggerCondition: condition,
        alertType: notificationMethods.join(','),
        status: 'active'
    };

    try {
        // Send data to backend
        const response = await fetch(ENDPOINTS.CREATE_ALERT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(alert)
        });

        if (!response.ok) {
            throw new Error('Failed to create alert');
        }

        const createdAlert = await response.json();
        showConfirmation(createdAlert);
        document.getElementById('alertForm').reset();
        document.getElementById('currentPrice').textContent = '';
        currentMarketData = null;
    } catch (error) {
        showError('submit', 'Failed to create alert. Please try again.');
        console.error('Error creating alert:', error);
    }

    return false;
}

function validateForm(ticker, targetPrice, notificationMethods) {
    let isValid = true;
    
    // Clear previous error messages
    clearErrors();

    // Validate ticker
    if (!ticker) {
        showError('ticker', 'Ticker symbol is required');
        isValid = false;
    }

    // Validate target price
    if (isNaN(targetPrice) || targetPrice <= 0) {
        showError('targetPrice', 'Target price must be a positive number');
        isValid = false;
    }

    // Validate notification methods
    if (notificationMethods.length === 0) {
        showError('push', 'At least one notification method must be selected');
        isValid = false;
    }

    return isValid;
}

function showError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error';
    errorDiv.textContent = message;
    field.parentNode.appendChild(errorDiv);
}

function clearErrors() {
    const errors = document.querySelectorAll('.error');
    errors.forEach(error => error.remove());
}

function showConfirmation(alert) {
    const confirmationDiv = document.getElementById('confirmation');
    const alertDetailsDiv = document.getElementById('alertDetails');
    
    alertDetailsDiv.innerHTML = `
        <p><strong>Ticker:</strong> ${alert.ticker}</p>
        <p><strong>Target Price:</strong> $${alert.targetPrice.toFixed(2)}</p>
        <p><strong>Condition:</strong> ${alert.condition}</p>
        <p><strong>Notification Methods:</strong> ${alert.notificationMethods.join(', ')}</p>
    `;
    
    confirmationDiv.classList.remove('hidden');
    
    // Reset form
    document.getElementById('alertForm').reset();
}