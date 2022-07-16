package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Request.UserLoginReq;
import model.User;
import repository.UserRep;

@Service
public class UserService {

	@Autowired
	private UserRep userrep;

	public User validateUserNameAndPassword(UserLoginReq UserLoginReq) throws ApplicationException {

		return userrep.validateUserNameAndPassword(UserLoginReq.getUserName(), UserLoginReq.getPassword());
	}

	public void saveuser(User user) {

		try {

			userrep.save(user);
		} catch (Exception exc) {

			exc.printStackTrace();
		}

	}

	public void updateuser(String name, long recid, String password) {

		try {

			userrep.update(name, recid, password);
		} catch (Exception exc) {

			exc.printStackTrace();

		}

	}

	public void updatelistofuser(List<User> user) {

		try {

			userrep.saveAll(user);
		} catch (Exception exc) {

			exc.printStackTrace();

		}

	}

	public void updateuserobject(User user) {

		try {

			userrep.save(user);
		} catch (Exception exc) {
			exc.printStackTrace();

		}

	}

	public void delete(long id) {
		try {
			System.out.println("ID existsssss and Deleted .....");
			userrep.deleteById(id);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public Optional<User> searchById(long id) {
		try {
			System.out.println("ID existsssss.....");
			return userrep.findById(id);
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	public List<User> getAlluser() {
		// if(userrep.getAlluser()!=null)

		try {

			return userrep.findAll();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;

	}

	public List<User> getAllofuser() {
		List<User> users = (List<User>) userrep.getAll();
		return users;

	}

	public User finduserbyname(User userreq) {

		return userrep.findUserName(userreq.getUsername());

	}

	public void mapReapExcelDatatoDB(MultipartFile reapExcelDataFile) throws IOException {

		List<User> tempStudentList = new ArrayList<User>();
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());

		XSSFSheet worksheet = workbook.getSheetAt(0);
		XSSFSheet worksheet2 = workbook.getSheetAt(1);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			workbook.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);

			for (int p = 1; p < worksheet2.getPhysicalNumberOfRows(); p++) {
				User tempStudent = new User();
				User tempStudent2 = new User();

				XSSFRow row = worksheet.getRow(i);
				XSSFRow row2 = worksheet2.getRow(p);

				tempStudent.setName(row.getCell(0).getStringCellValue());

				tempStudent.setPassword(row.getCell(1).getStringCellValue());
				tempStudent.setUsername(row.getCell(2).getStringCellValue());
				Double storege = (row.getCell(3) == null ? null : row.getCell(3).getNumericCellValue());

				if (storege != null) {

					tempStudent.setStorageid(storege.intValue());
				}

				// tempStudent.setStorageid
				// tempStudent.setStorageid(Long.parseLong(row.getCell(3).getStringCellValue()));
				// tempStudent.setType(row.getCell(4).getBooleanCellValue());
				row.getCell(4).getStringCellValue();
				tempStudentList.add(tempStudent);

				saveuser(tempStudent);

				tempStudent2.setName(row2.getCell(0).getStringCellValue());
				tempStudent2.setPassword(row2.getCell(1).getStringCellValue());
				tempStudent2.setUsername(row2.getCell(2).getStringCellValue());
				tempStudent2.setStorageid((int) (row2.getCell(3).getNumericCellValue()));
				tempStudent2.setType(row2.getCell(4).getBooleanCellValue());
				saveuser(tempStudent2);

			}
		}

	}

}
