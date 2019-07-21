package com.charlieworld.bookbug.controller;

import com.charlieworld.bookbug.dto.BaseResponse;
import com.charlieworld.bookbug.dto.BookDetail;
import com.charlieworld.bookbug.dto.Meta;
import com.charlieworld.bookbug.exception.CustomException;
import com.charlieworld.bookbug.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/v1/books/{bookId}")
    public BaseResponse getBookDetail(@PathVariable("bookId") Long bookId) {
        BookDetail bookDetail = null;
        BaseResponse<BookDetail> response = null;
        try {
            bookDetail = bookService.getBookDetail(bookId);
            response = new BaseResponse<BookDetail>(bookDetail);
        } catch (CustomException e) {
            Meta meta = new Meta(e.getStatusCode(), e.getMessage());
            response = new BaseResponse<BookDetail>(meta);
        }
        return response;
    }
}
