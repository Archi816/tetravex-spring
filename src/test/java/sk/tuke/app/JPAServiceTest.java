package sk.tuke.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.app.entity.Score;
import sk.tuke.app.entity.Rating;
import sk.tuke.app.entity.Comment;
import sk.tuke.app.service.CommentServiceJPA;
import sk.tuke.app.service.RatingServiceJPA;
import sk.tuke.app.service.ScoreServiceJPA;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JPAServiceTest {

    @Nested
    public class ScoreServiceJPATest {

        @Mock
        private EntityManager entityManager;

        @InjectMocks
        private ScoreServiceJPA scoreService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testAddScore() {
            Score score = new Score("game1", "player1", 100, new Date());
            scoreService.addScore(score);

            verify(entityManager, times(1)).persist(score);
        }

        @Test
        public void testGetTopScores() {
            Score score1 = new Score("game1", "player1", 100, new Date());
            Score score2 = new Score("game1", "player2", 150, new Date());

            TypedQuery<Score> queryMock = mock(TypedQuery.class);
            when(entityManager.createNamedQuery("Score.getTopScores", Score.class)).thenReturn(queryMock);
            when(queryMock.setParameter("game", "game1")).thenReturn(queryMock);
            when(queryMock.setMaxResults(10)).thenReturn(queryMock);
            when(queryMock.getResultList()).thenReturn(Arrays.asList(score1, score2));

            List<Score> topScores = scoreService.getTopScores("game1");

            assertNotNull(topScores);
            assertEquals(2, topScores.size());
            assertTrue(topScores.contains(score1));
            assertTrue(topScores.contains(score2));
        }
    }

    @Nested
    public class RatingServiceJPATest {

        @Mock
        private EntityManager entityManager;

        @InjectMocks
        private RatingServiceJPA ratingService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);  // Initialize mocks
        }

        @Test
        public void testSetRating() {
            Rating rating = new Rating("game1", "player1", 5, new Date());
            ratingService.setRating(rating);

            verify(entityManager, times(1)).persist(rating);
        }

        @Test
        public void testGetRating() {
            TypedQuery<Integer> queryMock = mock(TypedQuery.class);
            when(entityManager.createNamedQuery("Rating.getRating", Integer.class)).thenReturn(queryMock);
            when(queryMock.setParameter("game", "game1")).thenReturn(queryMock);
            when(queryMock.setParameter("player", "player1")).thenReturn(queryMock);
            when(queryMock.getSingleResult()).thenReturn(5);

            int retrievedRating = ratingService.getRating("game1", "player1");

            assertEquals(5, retrievedRating, "The rating should be 5");
        }

        @Test
        public void testGetAverageRating() {
            TypedQuery<Double> queryMock = mock(TypedQuery.class);
            when(entityManager.createNamedQuery("Rating.getAverageRating", Double.class)).thenReturn(queryMock);
            when(queryMock.setParameter("game", "game1")).thenReturn(queryMock);
            when(queryMock.getSingleResult()).thenReturn(4.5);

            int averageRating = ratingService.getAverageRating("game1");

            assertEquals(4, averageRating, "The average rating should be 4");
        }

    }

    @Nested
    public class CommentServiceJPATest {

        @Mock
        private EntityManager entityManager;

        @InjectMocks
        private CommentServiceJPA commentService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);  // Initialize mocks
        }

        @Test
        public void testAddComment() {
            Comment comment = new Comment("game1", "player1", "Great Game!", new Date());
            commentService.addComment(comment);

            verify(entityManager, times(1)).persist(comment);
        }

        @Test
        public void testGetComments() {
            Comment comment1 = new Comment("game1", "player1", "Great Game!", new Date());
            Comment comment2 = new Comment("game1", "player2", "Awesome!", new Date());

            // Mock the TypedQuery and EntityManager behavior
            TypedQuery<Comment> queryMock = mock(TypedQuery.class);
            when(entityManager.createNamedQuery("Comment.getComments", Comment.class)).thenReturn(queryMock);
            when(queryMock.setParameter("game", "game1")).thenReturn(queryMock);
            when(queryMock.setMaxResults(20)).thenReturn(queryMock);
            when(queryMock.getResultList()).thenReturn(Arrays.asList(comment1, comment2));

            List<Comment> comments = commentService.getComments("game1");

            assertNotNull(comments);
            assertEquals(2, comments.size());
            assertTrue(comments.contains(comment1));
            assertTrue(comments.contains(comment2));
        }
    }
}
