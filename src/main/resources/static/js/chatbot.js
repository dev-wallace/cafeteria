document.addEventListener('DOMContentLoaded', () => {
    const N8N_WEBHOOK_URL = 'http://localhost:5678/webhook/chat-ia';
    // Para teste, use:
    // const N8N_WEBHOOK_URL = 'http://localhost:5678/webhook-test/chat-ia';

    const chatFab = document.getElementById('chatFab');
    const chatWidget = document.getElementById('chatWidget');
    const chatClose = document.getElementById('chatClose');
    const chatBox = document.getElementById('chatBox');
    const userInput = document.getElementById('userInput');
    const sendButton = document.getElementById('sendButton');

    if (!chatFab || !chatWidget || !chatClose || !chatBox || !userInput || !sendButton) {
        return;
    }

    function generateUserId() {
        const key = 'cafeteria_chat_user_id';
        let userId = localStorage.getItem(key);

        if (!userId) {
            if (window.crypto && crypto.randomUUID) {
                userId = crypto.randomUUID();
            } else {
                userId = 'user-' + Date.now() + '-' + Math.floor(Math.random() * 1000000);
            }
            localStorage.setItem(key, userId);
        }

        return userId;
    }

    function toggleChat(open) {
        const isOpen = typeof open === 'boolean'
            ? open
            : !chatWidget.classList.contains('open');

        chatWidget.classList.toggle('open', isOpen);
        chatWidget.setAttribute('aria-hidden', String(!isOpen));

        if (isOpen) {
            chatFab.innerHTML = '<i class="fas fa-xmark"></i>';
            setTimeout(() => userInput.focus(), 50);
        } else {
            chatFab.innerHTML = '<i class="fas fa-mug-hot"></i>';
        }
    }

    function addMessage(text, sender) {
        const message = document.createElement('div');
        message.className = `message ${sender}`;
        message.textContent = text;
        chatBox.appendChild(message);
        chatBox.scrollTop = chatBox.scrollHeight;
        return message;
    }

    function setLoading(isLoading) {
        sendButton.disabled = isLoading;
        userInput.disabled = isLoading;
        sendButton.textContent = isLoading ? 'Enviando...' : 'Enviar';
    }

    async function sendMessage() {
        const message = userInput.value.trim();
        if (!message) return;

        addMessage(message, 'user');
        userInput.value = '';

        const typingMessage = addMessage('Digitando...', 'bot');
        setLoading(true);

        try {
            const response = await fetch(N8N_WEBHOOK_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    message: message,
                    userId: generateUserId()
                })
            });

            let data = null;
            const contentType = response.headers.get('content-type') || '';

            if (contentType.includes('application/json')) {
                data = await response.json();
            } else {
                const text = await response.text();
                data = { output: text };
            }

            typingMessage.remove();

            const botReply =
                data?.output ??
                data?.response ??
                data?.text ??
                data?.message ??
                (typeof data === 'string' ? data : null) ??
                'Não consegui ler a resposta da IA.';

            addMessage(botReply, 'bot');
        } catch (error) {
            typingMessage.remove();
            addMessage('Erro ao conectar com a IA. Verifique o n8n e a URL do webhook.', 'system');
            console.error(error);
        } finally {
            setLoading(false);
            userInput.focus();
        }
    }

    chatFab.addEventListener('click', () => toggleChat());
    chatClose.addEventListener('click', () => toggleChat(false));
    sendButton.addEventListener('click', sendMessage);

    userInput.addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendMessage();
        }
    });
});