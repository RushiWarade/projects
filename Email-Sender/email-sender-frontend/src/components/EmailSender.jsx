import React, { useRef, useState } from 'react';
import toast from 'react-hot-toast';
import { sendEmail, sendEmailWithFile } from '../services/email.service';
import { Editor } from '@tinymce/tinymce-react';
import { customAxios } from '../services/helper';
;

const EmailSender = () => {


  const [emailData, setEmailData] = useState({
    to: "",
    subject: "",
    message: "",
  });


  const MAX_FILE_SIZE = 10 * 1024 * 1024; // 50MB


  const [sending, setSending] = useState(false);
  const [file, setFile] = useState(null);
  const editorRef = useRef(null);

  // handle field change function
  const handleFieldChange = (event) => {
    setEmailData({ ...emailData, [event.target.name]: event.target.value })
  }





  // handle submit function
  const handleSubmit = async (event) => {
    event.preventDefault();
    if (file.size > MAX_FILE_SIZE) {
      toast.error("File is too large. Maximum size is 10MB!");
      return;
    }

    if (emailData.to.trim() === "" || emailData.message.trim() === "" || emailData.subject.trim() === "") {
      toast.error("Invalid fields");
      return;
    }


    try {
      setSending(true);
      if (file != null) {
        await sendEmailWithFile(emailData, file);
      } else {
        await sendEmail(emailData);
      }
      toast.success("Email sent successfully!");

      setEmailData({
        to: "",
        subject: "",
        message: ""
      });
      setFile(null);

      event.target.reset();
    } catch (error) {
      console.log(error);
      toast.error("Email not sent");
    } finally {
      setSending(false);
    }
  };




  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    setFile(selectedFile);

  };





  return (
    <div className='w-full min-h-screen flex mt-5 justify-center items-center' >
      <div className="email_card md:w-1/2 w-full mx-4 p-4  rounded-lg bg-white dark:bg-gray-700 dark:text-white dark:border-none border shadow">
        <h1 className='text-gray-900 text-3xl dark:text-gray-200'>Email Sender</h1>
        <p className='text-gray-700 dark:text-gray-300'>Send email to your faviritr person with your own app..</p>
        <form onSubmit={handleSubmit} encType='multipart/form-data' >
          <div className="input_field mt-4">
            <label htmlFor="large-input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">To</label>
            <input value={emailData.to} name='to' onChange={handleFieldChange} type="text" id="base-input" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder='Enter here' />
          </div>


          <div className="input_field mt-4">
            <label htmlFor="large-input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Subject</label>
            <input value={emailData.subject} name='subject' onChange={handleFieldChange} type="text" id="base-input" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder='Enter here' />

          </div>

          <div className="form_field  mt-4">
            <label htmlFor="message" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your message</label>


            <Editor
              onEditorChange={(event) => {
                setEmailData({ ...emailData, 'message': editorRef.current.getContent() })
              }}


              onInit={(_evt, editor) => editorRef.current = editor}
              apiKey='5ys15hk0i121ukylawn6leduwykict6a5czezt3dr8iw8pli'
              init={{
                plugins: 'anchor autolink charmap codesample emoticons  link lists  searchreplace table visualblocks  checklist  casechange export formatpainter  linkchecker a11ychecker tinymcespellchecker permanentpen powerpaste advtable advcode  advtemplate  mentions  tableofcontents footnotes mergetags autocorrect typography inlinecss markdown',

                // image media mediaembed editimage tinycomments pageembed wordcount


                toolbar: ' blocks   | bold italic underline strikethrough| align lineheight | checklist numlist bullist indent outdent | table | fontsize fontfamily| addcomment showcomments | spellcheckdialog a11ycheck typography   emoticons charmap | removeformat',
                height: 300,

                // undo redo |
                tinycomments_mode: 'embedded',
                tinycomments_author: 'Author name',
                mergetags_list: [
                  { value: 'First.Name', title: 'First Name' },
                  { value: 'Email', title: 'Email' },
                ],

              }}

            />





            {/* <textarea value={emailData.message} onChange={handleFieldChange} id="message" rows="5" className="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Write your thoughts here..."></textarea> */}

          </div>



          <div className="input_field mt-4">
            <label htmlFor="attachment" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Attachment</label>
            <input type="file" id="attachment" onChange={handleFileChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />

          </div>



          {sending &&
            <div className="loader flex-col gap-2 items-center flex justify-center mt-4">
              <div role="status">
                <svg aria-hidden="true" className="inline w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                  <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                </svg>
                <span className="sr-only">Loading...</span>
              </div>
              <h1>Sending Email please wait...</h1>
            </div>
          }

          <div className="button_container mt-4 flex justify-center gap-2">
            <button type="submit" disabled={sending} className='bg-blue-700 text-white hover:bg-blue-900 px-3 py-2 rounded ' > {sending ? "Sending.." : "Send Email"} </button>
            <button type='reset' className='bg-gray-500 text-white hover:bg-gray-900 px-3 py-2 rounded' >Clear</button>
          </div>




        </form>
      </div>
    </div>
  )
}

export default EmailSender