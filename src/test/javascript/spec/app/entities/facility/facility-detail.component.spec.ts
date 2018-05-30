/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { FacilityDetailComponent } from '../../../../../../main/webapp/app/entities/facility/facility-detail.component';
import { FacilityService } from '../../../../../../main/webapp/app/entities/facility/facility.service';
import { Facility } from '../../../../../../main/webapp/app/entities/facility/facility.model';

describe('Component Tests', () => {

    describe('Facility Management Detail Component', () => {
        let comp: FacilityDetailComponent;
        let fixture: ComponentFixture<FacilityDetailComponent>;
        let service: FacilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [FacilityDetailComponent],
                providers: [
                    FacilityService
                ]
            })
            .overrideTemplate(FacilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Facility(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.facility).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
