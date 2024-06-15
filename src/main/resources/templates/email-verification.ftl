<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Successful Mail</title>
    <!--[if !mso]><!-->
    <style>
        /* Normalize space between bullets and text. */
        /* https://litmus.com/community/discussions/1093-bulletproof-lists-using-ul-and-li */
        li {
            text-indent: -1em;
        }

        .tw-content {
            font-size: 16px;
            color: #000; /* Black color for content */
        }

        .tw-hello {
            font-size: 24px;
            margin: 0 0 20px;
            color: #474747;
        }
    </style>
    <!--<![endif]-->
    <!--[if gte mso 9]>
    <style type="text/css">
        /* What it does: Normalize space between bullets and text. */
        /* https://litmus.com/community/discussions/1093-bulletproof-lists-using-ul-and-li */
        li {
            text-indent: -1em;
        }
    </style>
    <![endif]-->
</head>

<body style="margin: 0; padding: 0; font-family: sans-serif; background-color: #F7F8FA;">

    <table style="background: #F7F8FA; border: 0; border-radius: 0; width: 100%; margin: 0 auto;" cellspacing="0"
        cellpadding="0">
        <tbody>
            <tr>
                <td style="padding: 15px 15px 0;" align="center">
                    <table style="background: #F7F8FA; border: 0; border-radius: 0;" cellspacing="0" cellpadding="0">
                        <tbody>
                            <tr>
                                <td style="width: 600px;" align="center">
                                    <p style="padding: 5px; font-size: 13px; margin: 0 0 0; color: #316fea;" align="right">
                                    </p>
                                    <table style="background: #ffffff; border: 0px; border-radius: 4px; width: 100%;"
                                        cellspacing="0" cellpadding="0">
                                        <tbody>
                                            <tr>
                                                <td style="padding: 0px;" align="center">
                                                    <table style="background: #336f85; border: 0px; border-radius: 0px; width: 100%; height: 53px; margin: 0 auto;"
                                                        cellspacing="0" cellpadding="0">
                                                        <tbody>
                                                           <tr>
                                                                <td class="tw-card-header"
                                                                       style="padding: 5px 5px px; width: 366px; color: #ffff; text-decoration: none; font-family: sans-serif;"
                                                                       align="center"><span
                                                                           style="font-weight: 600;">Registration and Email Verification</span>
																</td>
															</tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="tw-content" style="padding: 20px 35px; text-align: left; color: #6f6f6f; font-family: sans-serif; border-top: 0;">
                                                    <p class="tw-hi" style="margin: 0 0 20px; font-size: 16px; line-height: 24px; color: #000;">
                                                        Dear ${firstName},</p>
                                                    <p class="tw-content" style="margin: 0 0 20px; font-size: 16px; line-height: 24px; color: #000;">
                                                        Your registration has been successfully completed. Below are your account details:
                                                    </p>
                                                    <p class="tw-content" style="margin: 0 0 20px; font-size: 16px; line-height: 24px; color: #000;">
                                                        <b>Email ID:</b> ${emailId}<br>
                                                        <b>Password:</b> ${password}
                                                    </p>
                                                    <p class="tw-content" style="margin: 20px 0; font-size: 16px; line-height: 24px; color: #000;">
                                                        Thank you for joining Alphadot Technologies!! We are excited to have you as part of our team.<br>
                                                        For security reasons, it's important to change your password periodically. Follow these steps to update your password:<br>
                                                        Go to -> login -> account settings -> change password<br>
                                                        To complete the registration process, please confirm your email address to activate your account
                                                    </p>
                                                    <table style="border: 0; width: 100%;" cellspacing="0" cellpadding="0">
                                                        <tbody>
                                                            <tr>
                                                                <td>
                                                                    <table style="border-radius: 7px; margin: 0 auto; width: 525px; background-color: #008bcb; height: 50px;"
                                                                        cellspacing="0" cellpadding="0" align="center">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td style="text-align: center; width: 523px;">
                                                                                    <a style="border-radius: 4px; color: #ffffff; display: block; font-family: sans-serif; font-size: 18px; font-weight: normal; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 0;"
                                                                                        href="${userEmailTokenVerificationLink}"
                                                                                        target="_blank"
                                                                                        rel="noopener">Confirm
                                                                                        Email</a>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <div style="font-size: 1px; line-height: 1px; max-height: 0; margin: 20px 0; overflow: hidden;">
                                                        &nbsp;
                                                    </div>
                                                    <p class="tw-content" style="margin: 20px 0; font-size: 16px; line-height: 24px; color: #000;">
                                                        Contact our support team if you have any questions or concerns.&nbsp;<a
                                                            style="color: #316fea; text-decoration: none;"
                                                            href="javascript:void(0);" target="_blank" rel="noopener">Ask
                                                            us any question</a></p>
                                                    <p class="tw-content" style="margin: 45px 0 5px; font-size: 16px; line-height: 24px; color: #000;">
                                                        Best Regards, <br> Alphadot Technologies
                                                    </p>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>

</body>

</html>
