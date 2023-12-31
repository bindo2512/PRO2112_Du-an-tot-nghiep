package com.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dao.bookDAO;
import com.dao.categoriesDAO;
import com.entity.books;
import com.entity.comments;
import com.service.bookService;

@Service
public class bookServiceImpl implements bookService {
    
    @Autowired
    bookDAO dao;

    @Autowired
    categoriesDAO cDao;

    @Override
    public List<books> findAll(){
        return dao.findAll();
    }

    @Override
    public books findById(Integer id) {
        return dao.findById(id).get();
    }

    @Override
    public books createNewBook(books books) {
        return dao.save(books);
    }

    @Override
    public List<books> findBooksByName(String bookName) {
        return dao.findByBname(bookName);
    }

    @Override
    public Page<books> findBookByCriteria(Integer authorid, Integer publishersid , Integer categoriesid, String sortBy,
            String booknamekeyword, int page, int page_size) {
        Pageable pageable;
        pageable = PageRequest.of(page, page_size, Sort.unsorted());
        Specification<books> spec = Specification.where(null);
        switch (sortBy) {
            case "bnameASC":
                pageable = PageRequest.of(page, page_size, Sort.by("bname.bookname"));
                break;
            case "bnameDESC":
                pageable = PageRequest.of(page, page_size, Sort.by("bname.bookname").descending());
                break;
            case "yearReleaseASC":
                pageable = PageRequest.of(page, page_size, Sort.by("bname.bookreleaseyear"));
                break;
            case "yearReleaseDESC":
                pageable = PageRequest.of(page, page_size, Sort.by("bname.bookreleaseyear").descending());
                break;
            case "publishersASC":
                pageable = PageRequest.of(page, page_size, Sort.by("publishers.publishername"));
                break;
            case "publishersDESC":
                pageable = PageRequest.of(page, page_size, Sort.by("publishers.publishername").descending());
                break;
            case "issuerASC": 
                pageable = PageRequest.of(page, page_size, Sort.by("issuer.issuername"));
                break;
            case "issuerDESC":
                pageable = PageRequest.of(page, page_size, Sort.by("issuer.issuername").descending());
            default:
                pageable = PageRequest.of(page, page_size, Sort.unsorted());
                break;
        }

        if (authorid != null) {
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("authors").get("authorid"), authorid)));
        }
        if ( publishersid != null) {
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("publishers").get("publisherid"), publishersid)));
        }
        if ( categoriesid != null ){
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categories").get("categoryid"), categoriesid)));
        }
        if ( booknamekeyword != null) {
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("bname").get("bookname"), "%" + booknamekeyword + "%")));
        }
        return dao.findAll(spec, pageable);
    }

    @Override
    public books update(books book) {
        return dao.save(book);
    }

    @Override
    public Page<books> findTop5Lastest() {
        return dao.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdate")));
    }

    @Override
    public List<comments> getAllCommentByBookid(Integer id) {
        return dao.findById(id).get().getComments();
    }

}
