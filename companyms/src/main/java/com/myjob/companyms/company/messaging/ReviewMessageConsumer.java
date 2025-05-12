package com.myjob.companyms.company.messaging;

import com.myjob.companyms.company.CompanyService;
import com.myjob.companyms.company.clients.ReviewClient;
import com.myjob.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    private final ReviewClient reviewClient;

    public ReviewMessageConsumer(CompanyService companyService, ReviewClient reviewClient) {
        this.companyService = companyService;
        this.reviewClient = reviewClient;
    }


    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updateCompanyRating(reviewMessage);
    }

}
