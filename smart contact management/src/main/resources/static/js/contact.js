// console.log("contact js page");

const viewContactModal = document.getElementById("view_contact_modal");

const baseURL = "http://localhost:8080";
// const baseURL = "http://192.168.247.215:8080";

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: false,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions)


function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {
    console.log(id);
    try {
        const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();
        // console.log(data.id);

        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_contact").innerHTML = data.phoneNumber;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_desc").innerHTML = data.description;
        document.querySelector("#contact_linkdin").innerHTML = data.linkdinLink;
        document.querySelector("#contact_linkdin").href = data.linkdinLink;
        document.querySelector("#contact_website").innerHTML = data.websiteLink;
        const website = document.querySelector("#contact_website").innerHTML = data.websiteLink;
        document.querySelector("#contact_image").src = data.picture;
        const sendEmail = document.querySelector("#send_email");

        sendEmail.href = `/user/contact/email/${data.id}`;

        // (website.data.websiteLink != "") ? website.style.display = none : website.style.display = "";

        openContactModal();
    } catch (error) {
        console.log(error);
    }
}



function deleteContact(id) {
    console.log(id);

    const swalWithTailwindButtons = Swal.mixin({
        customClass: {
            confirmButton: "bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded",
            cancelButton: "bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
        },
        buttonsStyling: false
    });

    swalWithTailwindButtons.fire({
        title: "Are you sure?",
        text: "You won't to delete this contact!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            // swalWithTailwindButtons.fire({
            //     title: "Deleted!",
            //     text: "Your file has been deleted.",
            //     icon: "success"
            // });
            window.location.href = `${baseURL}/user/contact/delete/${id}`;
        } else if (
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swalWithTailwindButtons.fire({
                title: "Cancelled",
                text: "Your imaginary file is safe :)",
                icon: "error"
            });
        }
    });
}






