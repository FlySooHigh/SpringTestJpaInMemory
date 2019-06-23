import org.flysoohigh.config.StudentJpaConfig;
import org.flysoohigh.model.Student;
import org.flysoohigh.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {StudentJpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDbTest {

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private EntityManager entityManager;


    @Test
    public void givenStudent_whenSave_thenGetOk() {
        Student student = new Student(1, "john");
        studentRepository.save(student);

        Student student2 = studentRepository.findOne(1L);
        assertEquals("john", student2.getName());

        System.out.println(entityManager.toString());

        Student student1 = entityManager.find(Student.class, student.getId());
//        entityManager.merge(student1);
        entityManager.flush();

        entityManager.refresh(student1);
    }
}