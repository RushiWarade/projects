import { customAxios } from "./helper";

export async function sendEmail(emailData) {
    const result = await customAxios.post(`/email/send`, emailData);
    return result.data;
}

export async function sendEmailWithFile(emailData, file) {
    let formData = new FormData();
    formData.append('emailRequest', new Blob([JSON.stringify(emailData)], { type: 'application/json' }));
    formData.append('file', file);

    try {
        const result = await customAxios.post(`/email/send-with-file`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    } catch (error) {
        console.error('Error sending email:', error);
        throw error;
    }
}